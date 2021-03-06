package service;

import bean.Lender;
import bean.LoanQuote;
import exception.InsufficientLendersException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ROUND_UP;

public class LoanQuoteCalculator {

    /**
     * Total number of repayment months over the entire loan
     */
    public static final int REPAYMENT_MONTHS = 36;

    /**
     * the lenders available for loans
     */
    public static List<Lender> lenders = null;

    public Collection<Lender> getLenders() {
        return lenders;
    }

    public LoanQuoteCalculator(final List<Lender> lendersObj) {
        // sort lender based on cheapest rate and the largest amount
        lenders = lendersObj;

        this.lenders.sort((lender1, lender2) -> {
            final int rateComparison = lender1.getRate().compareTo(lender2.getRate());

            return rateComparison != 0 ? rateComparison : lender2.getAmount() - lender1.getAmount();
        });
    }

    public LoanQuote getQuote(int loanAmount) throws InsufficientLendersException {
        final Map<Lender, Integer> loans = getLendersForLoan(loanAmount);

        // calculate monthly repayment for each individual lender
        final BigDecimal monthlyRepayment = loans.entrySet().stream()

                // calculate total monthly repayment by calculating monthly repayment towards each individual lender
                .map(individualLoan -> {
                    final Lender lender = individualLoan.getKey();
                    final Integer individualLoanAmount = individualLoan.getValue();

                    return getMonthlyRepayment(lender.getRate(), individualLoanAmount);
                })

                // add up each monthly repayment
                .reduce(BigDecimal::add)

                // there must be at least one lender, so this is impossible
                .orElseThrow(() -> new IllegalStateException("getLendersForLoan should never return empty map"));

        // calculate total repayment based on non-rounded monthly repayment
        final BigDecimal totalRepayment = monthlyRepayment.multiply(new BigDecimal(REPAYMENT_MONTHS));

        // estimate interest rate based on monthly repayment
        final double rate = getApproximateAnnualInterestRate(loanAmount, monthlyRepayment);

        return new LoanQuote(
                loanAmount,

                // round annual interest rate to nearest one decimal place
                new BigDecimal(rate).setScale(1, ROUND_HALF_UP),

                // round monthly payment to nearest penny
                // customer might pay fractional pennies more/less every month
                // but the last payment can be adjusted to reflect this
                monthlyRepayment.setScale(2, ROUND_HALF_UP),

                // round up to ensure we do not lose fractional pennies, better for the customers to lose out than us having a shortfall
                totalRepayment.setScale(2, ROUND_UP)
        );
    }

    public double getApproximateAnnualInterestRate(int loanAmount, BigDecimal monthlyRepayment) {
        return AmortizedLoan.getApproximateAnnualInterestRate(loanAmount, REPAYMENT_MONTHS, monthlyRepayment.doubleValue()) * 100;
    }


    public BigDecimal getMonthlyRepayment(BigDecimal rate, Integer individualLoanAmount) {
        return AmortizedLoan.getMonthlyRepayment(new BigDecimal(individualLoanAmount), rate, REPAYMENT_MONTHS);
    }

    public Map<Lender, Integer> getLendersForLoan(int loanAmount) throws InsufficientLendersException {
        final Map<Lender, Integer> result = new HashMap<>();

        int remainingLoanAmount = loanAmount;

        for (final Lender lender : lenders) {
            // can this lender satisfy remaining loan required?
            if (lender.getAmount() >= remainingLoanAmount) {
                result.put(lender, remainingLoanAmount);

                return result;
            }

            // use up all of lender's quota
            result.put(lender, lender.getAmount());

            remainingLoanAmount -= lender.getAmount();
        }

        throw new InsufficientLendersException();
    }
}

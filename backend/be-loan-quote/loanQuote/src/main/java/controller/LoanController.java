package controller;

import bean.LoanQuote;
import dto.LoanQuoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.LoanQuoteApplication;

@CrossOrigin
@RestController
public class LoanController {

    @Autowired
    private LoanQuoteApplication loanQuoteApplication;

    @PutMapping(value = "http://127.0.0.1:8000/api/loan/calculate")
    public @ResponseBody LoanQuoteDTO calculateLoan(@RequestBody LoanQuoteDTO loanQuoteDTO) throws Exception {
        if (loanQuoteDTO.getLoanAmount() != 0) {
            return this.loanQuoteApplication.calculation(new LoanQuote(loanQuoteDTO.getLoanAmount()));
        } else {
            throw new Exception("No amount inserted!");
        }
    }
}

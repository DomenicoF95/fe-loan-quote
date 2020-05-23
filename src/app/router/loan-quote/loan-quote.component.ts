import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {LoanQuoteService} from '../../service/loan-quote.service';

@Component({
  selector: 'app-loan-quote',
  templateUrl: './loan-quote.component.html',
  styleUrls: ['./loan-quote.component.scss']
})
export class LoanQuoteComponent implements OnInit {

  public form = new FormGroup({});

  constructor(private fb: FormBuilder, private loanQuoteService: LoanQuoteService) { }

  ngOnInit() {
    this.form = this.fb.group({
      id: [0],
      loanAmount: ['', Validators.pattern('^(0|[1-9][0-9]*)$')],
    });
  }

  requestLoan() {
    this.loanQuoteService.postLoan(this.form.value).subscribe( resp => {
      console.log(JSON.stringify(resp));
    });
  }
}

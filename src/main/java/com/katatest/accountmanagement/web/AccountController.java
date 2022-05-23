package com.katatest.accountmanagement.web;

import com.katatest.accountmanagement.domain.Amount;
import com.katatest.accountmanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    /**
     * Service API to save money
     *
     * @param amount - object used to receive the amount of money to save
     * @return - a boolean to indicate if the operation was successful or not
     */
    @PostMapping("{accountId}/deposit")
    public ResponseEntity<Boolean> deposit(@PathVariable Long accountId, @Valid @RequestBody Amount amount) {
        log.info("Executing new deposit in account {} of {} {}", accountId, amount.getValue(), amount.getCurrency().getCurrencyCode());
        final Boolean isSaved = this.accountService.deposit(accountId, amount);

        if (Boolean.FALSE.equals(isSaved)) {
            log.error("error has occurred !");
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    /**
     * Service API to withdraw money
     *
     * @param amount - object used to receive the amount of money to withdraw
     * @return - a boolean to indicate if the operation was successful or not
     */
    @PostMapping("{accountId}/withdraw")
    public ResponseEntity<Boolean> withdraw(@PathVariable Long accountId, @Valid @RequestBody Amount amount) {
        log.info("Executing new withdrawal in account {} {} of {} ", accountId, amount.getValue(), amount.getCurrency().getCurrencyCode());
        final Boolean isSuccessful = this.accountService.withdraw(accountId, amount);

        if (Boolean.FALSE.equals(isSuccessful)) {
            log.error("error has occurred !");
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    /**
     * Service API to print an account statement
     *
     * @param accountId - the account id to print
     * @return - the string containing the account statement to print
     */
    @GetMapping("{accountId}/print")
    public ResponseEntity<String> print(@PathVariable Long accountId) {
        log.info("Printing the account {} statement ", accountId);

        return ResponseEntity.ok(accountService.printStatement(accountId));
    }
}

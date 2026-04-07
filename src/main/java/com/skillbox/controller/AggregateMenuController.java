package com.skillbox.controller;



import com.skillbox.controller.option.AggregateOption;
import com.skillbox.data.repository.AccountRepository;
import java.util.Scanner;

public class AggregateMenuController extends AbstractMenuController<AggregateOption> {
    private final AccountRepository accountRepository;

    public AggregateMenuController(AccountRepository accountRepository, Scanner scanner) {
        super(AggregateOption.class, "=== Агрегатные операции ===", scanner);
        this.accountRepository = accountRepository;
    }

    @Override
    protected void processSelectedOption(AggregateOption option) {

    }

    @Override
    protected boolean isExitOption(AggregateOption option) {
        return option == AggregateOption.BACK;
    }
}
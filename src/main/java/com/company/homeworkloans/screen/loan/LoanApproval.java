package com.company.homeworkloans.screen.loan;

import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

@UiController("LoanApproval")
@UiDescriptor("loan-approval.xml")
@LookupComponent("loansTable")
public class LoanApproval extends StandardLookup<Loan> {
    @Autowired
    private CollectionLoader<Loan> loansDl;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private GroupTable<Loan> loansTable;
    @Autowired
    private Notifications notifications;

    @Install(to = "loansTable.age", subject = "columnGenerator")
    private Component loansTableAgeColumnGenerator(Loan loan) {
        LocalDate birthDate = loan.getClient().getBirthDate();
        int year = Period.between(birthDate, LocalDate.now()).getYears();
        return new Table.PlainTextCell(Integer.toString(year));
    }

    @Subscribe("loansTable.approve")
    public void onApprove(Action.ActionPerformedEvent event) {
        Loan selected = loansTable.getSingleSelected();
        selected.setStatus(LoanStatus.APPROVED);
        dataManager.save(selected);
        notifications.create().withCaption("Approved")
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
        loansDl.load();
    }

    @Subscribe("loansTable.reject")
    public void onLoansTableReject(Action.ActionPerformedEvent event) {
        Loan selected = loansTable.getSingleSelected();
        selected.setStatus(LoanStatus.REJECTED);
        dataManager.save(selected);
        notifications.create().withCaption("Rejected")
                .withType(Notifications.NotificationType.HUMANIZED)
                .show();
        loansDl.load();
    }
}
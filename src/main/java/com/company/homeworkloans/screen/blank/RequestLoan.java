package com.company.homeworkloans.screen.blank;

import com.company.homeworkloans.entity.Client;
import com.company.homeworkloans.entity.Loan;
import com.company.homeworkloans.entity.LoanStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.TextField;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

@UiController("RequestLoan")
@UiDescriptor("blank-screen.xml")
public class RequestLoan extends Screen {
    @Autowired
    private EntityComboBox<Client> clientField;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private TextField amountField;

    @Subscribe("request")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        if(clientField.getValue() == null || amountField.getRawValue().isEmpty()) {
            throw new IllegalStateException("All fields must be filled");
        }
        Loan loan =  dataManager.create(Loan.class);
        loan.setClient(clientField.getValue());
        loan.setAmount(new BigDecimal(amountField.getRawValue()));
        loan.setRequestDate(LocalDate.now());
        loan.setStatus(LoanStatus.REQUESTED);
        dataManager.save(loan);
        closeWithDefaultAction();
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }
}
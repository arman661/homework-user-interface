package com.company.homeworkloans.screen.client;

import com.company.homeworkloans.screen.blank.RequestLoan;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.screen.*;
import com.company.homeworkloans.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("clientsTable.requestLoan")
    public void onRequestLoan(Action.ActionPerformedEvent event) {
        RequestLoan requestLoanScreen = screenBuilders.screen(this)
                .withScreenClass(RequestLoan.class)
                .build();
        requestLoanScreen.show();
    }
}
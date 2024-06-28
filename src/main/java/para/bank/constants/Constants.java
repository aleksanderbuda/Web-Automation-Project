package para.bank.constants;

import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.utils.config.Configuration;

public interface Constants {

        interface Urls {
            String URL = Configuration.getRequired("url");

            String PROD_URL = R.CONFIG.get("PROD.url");

            String LANDING_PAGE_URL = URL;

            String CREATE_ACCOUNT_URL = URL+  "parabank/register.htm";

            String OPEN_NEW_ACCOUNT_PAGE_URL = URL + "parabank/openaccount.htm";

            String ACCOUNTS_OVERVIEW_URL = URL + "parabank/overview.htm";

            String TRANSFER_FUNDS_URL = URL + "parabank/transfer.htm";

            String BILL_PAYMENT_PAGE_URL = URL + "parabank/billpay.htm";

            String FIND_TRANSACTION_URL =  URL + "parabank/findtrans.htm";

            String UPDATE_PROFILE_URL = URL + "parabank/updateprofile.htm";

            String REQUEST_LOAN_URL = URL + "parabank/requestloan.htm";

            String ADMIN_PAGE_URL = URL + "parabank/admin.htm";

            String ACCOUNT_ACTIVITY_URL = URL + "parabank/activity.htm";

            String TRANSACTION_DETAILS_URL = URL + "parabank/transaction.htm";

            String UPDATE_PROFILE_INFO_URL = URL + "parabank/updateprofile.htm";

            String ABOUT_URL = URL + "parabank/about.htm";
        }

        interface PageTitles {
            String LANDING_PAGE_TITLE = "ParaBank | Welcome | Online Banking";

            String CREATE_ACCOUNT_PAGE_TITLE = "ParaBank | Register for Free Online Account Access";

            String WELCOME_MESSAGE_TITLE = "Your account was created successfully. You are now logged in.";

            String OPEN_NEW_ACCOUNT_PAGE_TITLE = "ParaBank | Open Account";

            String ACCOUNTS_OVERVIEW_PAGE_TITLE = "ParaBank | Accounts Overview";

            String TRANSFER_FUNDS_PAGE_TITLE = "ParaBank | Transfer Funds";

            String TRANSFER_FUNDS_COMPLETED_PAGE_TITLE = "Transfer Complete!";

            String BILL_PAYMENT_PAGE_TITLE = "ParaBank | Bill Pay";

            String BILL_PAYMENT_COMPLETE_PAGE_TITLE = "Bill Payment Complete";

            String TRANSACTION_DETAILS_PAGE_TITLE = "ParaBank | Transaction Details";

            String FIND_TRANSACTIONS_PAGE_TITLE= "ParaBank | Find Transactions";

            String UPDATE_PROFILE_PAGE_TITLE = "ParaBank | Update Profile";

            String REQUEST_LOAN_PAGE_TITLE = "ParaBank | Loan Request";

            String ADMIN_PAGE_TITLE = "ParaBank | Administration";

            String ERROR_PAGE_TITLE = "ParaBank | Error";

            String ACCOUNT_ACTIVITY_PAGE_TITLE = "ParaBank | Account Activity";

            String ABOUT_PAGE_TITLE = "ParaBank | About Us";
        }
}

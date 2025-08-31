package com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement;

import android.app.Activity;
import android.widget.Toast;

import com.btracsolution.deliverysystem.Base.BaseItemClickListener;
import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.Model.StatementModel;
import com.btracsolution.deliverysystem.Utility.LogoutUtility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mahmudul.hasan on 3/20/2018.
 */

public class AgentStatementPresenter {

    Activity activity;
    AgentDayStatement agentDayStatement;
    AgentStatementModel agentStatementModel;

    BaseServerListener baseServerListener = new BaseServerListener() {
        @Override
        public void onServerSuccessOrFailure(boolean isSuccess, Object object, String message) {
            if (isSuccess) {
                StatementModel statementModel = (StatementModel) object;
                if (statementModel != null) {


                    agentDayStatement.setDetailsData(statementModel);
                    AgentStatementAdapter agentStatementAdapter = new AgentStatementAdapter(activity, statementModel.getResponseData().getIndividualDates(), new BaseItemClickListener() {
                        @Override
                        public void onClickOfListItem(boolean isClick, int position, String extra) {

                        }
                    });

                    if (statementModel.getResponseData().getIndividualDates().size() > 0)
                        agentDayStatement.yesDataFound();
                    else
                        agentDayStatement.noDataFound();

                    agentDayStatement.setAdapterIntoRecyclView(agentStatementAdapter);
                }
            } else {
                StatementModel statementModel = (StatementModel) object;
                if (statementModel == null)
                    agentDayStatement.noDataFound();
                else if (statementModel.getError().getError_code().contentEquals("401")) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    new LogoutUtility().setLoggedOut(activity);
                } else
                    agentDayStatement.noDataFound();
            }
        }
    };

    public AgentStatementPresenter(Activity activity, AgentDayStatement agentDayStatement) {
        this.activity = activity;
        this.agentDayStatement = agentDayStatement;
        agentStatementModel = new AgentStatementModel(activity, baseServerListener);
    }

    public static String getCalculatedDateBefore(int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public void getServerData(String typeOfSelection) {
        String todayDate = getTodayDate();
        String fromDate = null;

        switch (typeOfSelection) {
            case "last_week":
                fromDate = getCalculatedDateBefore(-7);
                break;
            case "last_month":
                fromDate = getCalculatedDateBefore(-30);

                break;
            case "last_year":
                fromDate = getCalculatedDateBefore(-365);
                break;
            case "last_custom":
                fromDate = agentDayStatement.fromDate;
                todayDate = agentDayStatement.toDate;

                break;
        }
        if (todayDate != null && fromDate != null) {
            if (agentStatementModel != null)
                agentStatementModel.getServerData(true, fromDate, todayDate);
        }
    }

    public String getTodayDate() {
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        return date;
    }


}

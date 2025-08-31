package com.btracsolution.deliverysystem.Depedency.di.components;

import com.btracsolution.deliverysystem.Depedency.di.modules.DoNetworkModule;
import com.btracsolution.deliverysystem.Depedency.di.myAnnotation.PerActivity;
import com.btracsolution.deliverysystem.Features.Agent.AgentHomeActivity;
import com.btracsolution.deliverysystem.Features.Agent.AgentMenuList.AgentMenuDetailsModel;
import com.btracsolution.deliverysystem.Features.Agent.AgentOrderDetails.AgentOrderDetailsModel;
import com.btracsolution.deliverysystem.Features.Agent.AgentSelectRider.AgentSelectRiderModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentDayWiseResult.AgentDayReport;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentMenuPac.AgentMenuModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentOrder.AgentModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.AgentRider.AgentAllRiderModel;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ActivityProfileAgent;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Profile.ProfileAgent;
import com.btracsolution.deliverysystem.Features.Agent.Fragments.Statement.AgentStatementModel;
import com.btracsolution.deliverysystem.Features.Forgot.ForgotModel;
import com.btracsolution.deliverysystem.Features.Forgot.ForgotOwnPresenter;
import com.btracsolution.deliverysystem.Features.Forgot.ForgotUpdatePasswordPresenter;
import com.btracsolution.deliverysystem.Features.Login.LoginActivity;
import com.btracsolution.deliverysystem.Features.Rider.Features.MyJobPack.RiderJobModel;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderOrderDetails.RiderOrderDetailsModel;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderProfile.ProfileRider;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder.RiderDayModel;
import com.btracsolution.deliverysystem.Features.Rider.Features.RiderRecentOrder.RiderDayReport;
import com.btracsolution.deliverysystem.Features.Rider.HomeActivity;
import com.btracsolution.deliverysystem.Features.Rider.LocationBackground.BackgroundLocationService;
import com.btracsolution.deliverysystem.Features.Rider.LocationBackground.BackgroundLocationUpdateModel;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Bill.BillRepo;
import com.btracsolution.deliverysystem.Features.Waiter.Features.BillHistory.BillHistoryRepository;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Cart.MyCartRepo;
import com.btracsolution.deliverysystem.Features.Waiter.Features.ProfileWaiter.ProfileWaiter;
import com.btracsolution.deliverysystem.Features.Waiter.Features.Order.Menu.WaiterMenuRepo;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterDayReport.WaiterDayModel;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterDayReport.WaiterDayReport;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJobRepo;
import com.btracsolution.deliverysystem.Features.Waiter.Features.WaiterJobList.WaiterJoblistDetail.WaiterOrderDetailRepo;
import com.btracsolution.deliverysystem.Features.Waiter.WaiterActivity;
import com.btracsolution.deliverysystem.Firebase.Agent.MyFirebaseMessagingService;

import dagger.Component;

/**
 * Created by mahmudul.hasan on 12/31/2017.
 */
@PerActivity
@Component(dependencies = NetComponent.class, modules = DoNetworkModule.class)
public interface DoNetworkComponent {

    void inject(BillHistoryRepository billHistoryRepository);
    void inject(BillRepo billRepo);
    void inject(WaiterOrderDetailRepo waiterOrderDetailRepo);
    void inject(WaiterJobRepo waiterJobRepo);
    void inject(ProfileWaiter profileWaiter);
    void inject(MyCartRepo myCartRepo);
    void inject(WaiterMenuRepo waiterMenuRepo);
    void inject(WaiterActivity waiterActivity);
    void inject(WaiterDayReport waiterDayReport);
    void inject(WaiterDayModel waiterDayModel);



    void inject(HomeActivity homeActivity);

    void inject(AgentHomeActivity agentHomeActivity);

    void inject(LoginActivity loginActivity);

    void inject(AgentModel agentModel);

    void inject(AgentDayModel agentModel);

    void inject(ProfileAgent agentModel);

    void inject(ActivityProfileAgent agentModel);
    void inject(AgentDayReport agentDayReport);

    void inject(AgentOrderDetailsModel agentOrderDetailsModel);

    void inject(AgentMenuModel agentMenuModel);

    void inject(AgentMenuDetailsModel agentMenuDetailsModel);

    void inject(AgentSelectRiderModel agentSelectRiderModel);

    void inject(AgentAllRiderModel agentSelectRiderModel);

    void inject(ProfileRider profileRider);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);

    void inject(RiderJobModel riderJobModel);

    void inject(BackgroundLocationUpdateModel backgroundLocationUpdateModel);

    void inject(BackgroundLocationService backgroundLocationService);

    void inject(RiderDayModel riderDayModel);

    void inject(RiderDayReport riderDayReport);

    void inject(RiderOrderDetailsModel riderOrderDetailsModel);

    void inject(ForgotOwnPresenter forgotOwnPresenter);

    void inject(ForgotModel forgotModel);

    void inject(ForgotUpdatePasswordPresenter forgotUpdatePasswordPresenter);

    void inject(AgentStatementModel agentStatementModel);

//    void inject(LoginActivity loginActivtiy);

}

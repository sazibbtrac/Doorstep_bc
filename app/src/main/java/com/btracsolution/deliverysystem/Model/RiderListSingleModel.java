package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 2/6/2018.
 */

public class RiderListSingleModel {

    /* {
status: "success",
message: "All Rider Info",
data: [
{
riderid: 9,
fullname: "test riderJobPresenter 2",
contactno: "445544444444",
email: "asdfasfda@asdfasd.cdc",
usertype: 4,
profileImage: null,
lat: 23.79129411,
lng: 90.40126422,
distance: 0.2318152609049464,
available: "1",
jobcompleted: 1
},
{
riderid: 6,
fullname: "test Delivery agent",
contactno: "000888",
email: "c@c.c",
profileImage: null,
usertype: 4,
lat: 23.79193222,
lng: 90.40951469,
distance: 0.6168181708900139,
available: "2",
jobcompleted: 0
}
]
}*/

    String status;
    String message;

    @Expose
    @SerializedName("data")
    ArrayList<riderInfo> riderListInfo;

    @Expose
    ErrorData error;


    public ArrayList<riderInfo> getRiderListInfo() {
        return riderListInfo;
    }

    public void setRiderListInfo(ArrayList<riderInfo> riderListInfo) {
        this.riderListInfo = riderListInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }


    public class riderInfo {
        String riderid;
        String fullname;
        String contactno;
        String email;
        String usertype;
        String profileImage;
        String loginstatus;
        String lat;
        String lng;
        String distance;
        String available;
        String jobcompleted;

        public String getLoginstatus() {
            return loginstatus;
        }

        public void setLoginstatus(String loginstatus) {
            this.loginstatus = loginstatus;
        }

        public String getRiderid() {
            return riderid;
        }

        public void setRiderid(String riderid) {
            this.riderid = riderid;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getContactno() {
            return contactno;
        }

        public void setContactno(String contactno) {
            this.contactno = contactno;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getJobcompleted() {
            return jobcompleted;
        }

        public void setJobcompleted(String jobcompleted) {
            this.jobcompleted = jobcompleted;
        }
    }


    public class ErrorData {
        String error_code;
        String error_msg;

        public String getError_code() {
            return error_code;
        }

        public void setError_code(String error_code) {
            this.error_code = error_code;
        }

        public String getError_msg() {
            return error_msg;
        }

        public void setError_msg(String error_msg) {
            this.error_msg = error_msg;
        }
    }

}

package com.btracsolution.deliverysystem.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemberListResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<MemberData> memberData = null;


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

    public void setMemberData(List<MemberData> memberData) {
        this.memberData = memberData;
    }

    public List<MemberData> getMemberData() {
        return memberData;
    }

    public class MemberData {

        @SerializedName("member_id")
        @Expose
        private String memberId;
        @SerializedName("member_name")
        @Expose
        private String memberName;
        @SerializedName("member_mobile")
        @Expose
        private String memberMobile;
        @SerializedName("member_image")
        @Expose
        private String memberImage;

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getMemberName() {
            return memberName;
        }

        public void setMemberName(String memberName) {
            this.memberName = memberName;
        }

        public String getMemberMobile() {
            return memberMobile;
        }

        public void setMemberMobile(String memberMobile) {
            this.memberMobile = memberMobile;
        }

        public String getMemberImage() {
            return memberImage;
        }

        public void setMemberImage(String memberImage) {
            this.memberImage = memberImage;
        }

    }
}

import React from "react";
import UpdateBio from "../ProfileComponents/UpdateBio";
import ViewProfile from "../ProfileComponents/ViewProfile";

function ProfilePage(){
    return (
        <div className="profile-container">
            <h1>Profile Page</h1>
            <div className="profile-section">
                <ViewProfile />
            </div>
            <div className="profile-section">
                <UpdateBio />
            </div>
        </div>
    );
}
export default ProfilePage;
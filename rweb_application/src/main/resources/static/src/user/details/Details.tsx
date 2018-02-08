import * as React from "react";
import {Component} from "react";
import {ProfileProperties} from "./Profile";
import GeneralDetails from "./GeneralDetails"
import ProfileSubscribers from "./ProfileSubscribers";
import ImageEntity from "../../model/ImageEntity";
import TextPart from "../../model/TextPart";
import Descriptions from "../../images/social/Descriptions";

export default class Details extends Component<ProfileProperties, {}> {

    // private image: ImageEntity = new ImageEntity([new TextPart("#aaa", true, "/ssdsd")], 1, "/l", "name", false, null);

    constructor(props: ProfileProperties) {
        super(props);
    }

    render(): React.ReactNode {
        const u = this.props.user;
        const username = u != null ? u.username : '';
        return (
            <div className="media-body profileDetails">
                <GeneralDetails self={this.props.self} user={this.props.user}
                                refreshUser={this.props.refreshUser}/>
                <ProfileSubscribers self={this.props.self} user={this.props.user}
                                    refreshUser={this.props.refreshUser}/>
                <div><h4 className="profileUsername"><b>{username}</b></h4></div>
                {/*<Descriptions image={this.image}/>*/}
            </div>
        );
    }
}
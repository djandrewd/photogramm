import * as React from "react";
import {Component} from "react";
import UserEntity from "../../model/UserEntity";
import Avatar from "./Avatar";

import "./Profile.css";
import Details from "./Details";

export interface ProfileProperties {
    user: UserEntity
    self: UserEntity
    refreshUser: () => void
}

export default class Profile extends Component<ProfileProperties, {}> {

    constructor(props: ProfileProperties) {
        super(props);
    }

    render(): React.ReactNode {
        return (
            <div className="media header">
                <Avatar/>
                <Details user={this.props.user} self={this.props.self}
                         refreshUser={this.props.refreshUser}/>
            </div>
        );
    }
}
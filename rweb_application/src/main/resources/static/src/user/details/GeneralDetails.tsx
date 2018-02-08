import * as React from "react";
import {Component} from "react";
import {ProfileProperties} from "./Profile";

import "./GeneralDetails.css";
import * as Cookies from "js-cookie";
import UserEntity from "../../model/UserEntity";


export default class GeneralDetails extends Component<ProfileProperties, {}> {
    private csrfToken: string;

    constructor(props: ProfileProperties) {
        super(props);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        const user = this.props.user;
        const nickname = user != null ? user.nickname : '';

        return (
            <div className="profileGeneral">
                <h5 className="profileNickname" title={nickname}><b>{nickname}</b></h5>
                <div className="editProfile">
                    {GeneralDetails.buildSocialButton(user, this.socialAction.bind(this))}
                </div>
            </div>
        );
    }

    public static buildSocialButton(user: UserEntity, action: (actionUrl: string) => void): JSX.Element {
        let actionUrl = '';
        let actionElement = <div/>;
        if (user != null) {
            if (user.subscribed) {
                const link = user.links.find(l => l.rel == "ubsubscribe");
                actionUrl = link != null? link.href: '';
            } else {
                const link = user.links.find(l => l.rel == "subscribe");
                actionUrl = link != null? link.href: '';
            }
            if (!user.self) {
                actionElement =
                    <button className="btn btn-xs btn-info subscribeUser" onClick={(e) => {
                        action(actionUrl)
                    }}>
                        {user != null && user.subscribed ? "Unsubscribe" : "Subscribe"}
                    </button>
            } else {
                actionElement = <button className="btn btn-default btn-xs subscribeUser">
                    <span className="glyphicon glyphicon-cog"/>
                </button>
            }
        }
        return actionElement;
    }

    private socialAction(actionUrl: string) {
        fetch(actionUrl, {
            method: 'POST',
            headers: new Headers({
                'X-XSRF-TOKEN': this.csrfToken
            }),
            credentials: "same-origin"
        }).then((r) => {
            if (r.status == 200) {
                this.props.refreshUser();
            }
        })
            .catch((e: any) => {
                console.log("Cannot save the message.." + e);
            });
    }
}
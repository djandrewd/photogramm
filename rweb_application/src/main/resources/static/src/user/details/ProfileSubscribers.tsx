import * as React from "react";
import {Component} from "react";
import {ProfileProperties} from "./Profile";

import "./ProfileSubscribers.css";
import UserEntity from "../../model/UserEntity";
import PopupList from "../../popup/PopupList";
import * as Cookies from "js-cookie";
import GeneralDetails from "./GeneralDetails";

interface ProfileSubscribersState {
    subscribersOpen: boolean
    subscribers: UserEntity[]
    subscriptionsOpen: boolean
    subscriptions: UserEntity[]
}

export default class ProfileSubscribers extends Component<ProfileProperties, ProfileSubscribersState> {

    private static SUBSCRIBERS_URL: string = "/social/subscribers/";
    private static SUBSCRIPTIONS_URL: string = "/social/subscriptions/";

    private csrfToken: string;

    constructor(props: ProfileProperties) {
        super(props);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
        this.state = {
            subscribersOpen: false,
            subscribers: [],
            subscriptionsOpen: false,
            subscriptions: []
        };
    }

    render(): React.ReactNode {
        const publications = this.props.user != null ? this.props.user.posts : 0;
        const subscriptions = this.props.user != null ? this.props.user.subscriptions : 0;

        return (
            <ul className="profileSubcribers">
                <li className="profileElement">{publications} publications</li>
                {this.buildSubscribersElement()}
                {this.buildSubscriptionsElement()}
            </ul>
        );
    }

    private buildSubscribersElement() {
        const subscribers = this.props.user != null ? this.props.user.subscribers : 0;
        return (
            <li className="profileElement">
                <button className="followerOpen btn btn-link btn-xs" onClick={() => {
                    this.toggleSubscribers()
                }}>{subscribers} subscribers
                </button>

                <PopupList open={this.state.subscribersOpen}
                           converter={(e: UserEntity) => this.buildFollower(e)}
                           keyProvider={(e: UserEntity) => e.nickname}
                           close={this.toggleSubscribers.bind(this)}
                           elements={this.state.subscribers}/>
            </li>);
    }

    private buildSubscriptionsElement() {
        const subscriptions = this.props.user != null ? this.props.user.subscriptions : 0;
        return (
            <li className="profileElement">
                <button className="followerOpen btn btn-link btn-xs" onClick={() => {
                    this.toggleSubscriptions()
                }}>Subscribe: {subscriptions}
                </button>

                <PopupList open={this.state.subscriptionsOpen}
                           converter={(e: UserEntity) => this.buildFollower(e)}
                           keyProvider={(e: UserEntity) => e.nickname}
                           close={this.toggleSubscriptions.bind(this)}
                           elements={this.state.subscriptions}/>
            </li>);
    }

    private buildFollower(user: UserEntity): JSX.Element {
        let actionElement = null;
        if (!user.self) {
            actionElement = GeneralDetails.buildSocialButton(user, this.socialAction.bind(this));
        }
        return (
            <div className="follower">
                <div className="followerNick">
                    <b>{user.nickname}</b> ({user.username})
                </div>
                <div className="followerBtn">
                    {actionElement}
                </div>
            </div>)
    }

    private toggleSubscriptions() {
        if (!this.state.subscriptionsOpen) {
            this.fetchSubscriptions()
        }
        this.setState({
            subscriptionsOpen: !this.state.subscriptionsOpen,
            subscribersOpen: false
        })
    }

    private toggleSubscribers() {
        if (!this.state.subscribersOpen) {
            this.fetchSubscribers()
        }
        this.setState({
            subscriptionsOpen: false,
            subscribersOpen: !this.state.subscribersOpen
        })
    }

    private fetchSubscribers() {
        fetch(ProfileSubscribers.SUBSCRIBERS_URL + this.props.user.nickname, {
            credentials: "same-origin"
        }).then(r => {
            if (r.ok) {
                r.json().then((users: UserEntity[]) => {
                    this.setState({
                        subscribers: users
                    })
                })
            }
        })
    }

    private fetchSubscriptions() {
        fetch(ProfileSubscribers.SUBSCRIPTIONS_URL + this.props.user.nickname, {
            credentials: "same-origin"
        }).then(r => {
            if (r.ok) {
                r.json().then((users: UserEntity[]) => {
                    this.setState({
                        subscriptions: users
                    })
                })
            }
        })
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
                // When user suscbribed on something we have to refresh.
                if (this.state.subscribersOpen) {
                    this.fetchSubscribers()
                }
                if (this.state.subscriptionsOpen) {
                    this.fetchSubscriptions()
                }
            }
        })
            .catch((e: any) => {
                console.log("Cannot save the message.." + e);
            });
    }
}

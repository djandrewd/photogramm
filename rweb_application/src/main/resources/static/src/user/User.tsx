import * as React from "react";
import {Component} from "react";

import "./User.css";
import {RouteComponentProps} from "react-router";
import UserEntity from "../model/UserEntity";
import Profile from "./details/Profile";
import ImageEntity from "../model/ImageEntity";
import UserImagesEntity from "../model/UserImagesEntity";
import Navigation from "../nav/Navigation";
import ImagesList from "../images/list/ImagesList";

interface UserProperties {
    nickname: string
}

interface UserState {
    user: UserEntity
    images: ImageEntity[]

    // Information about current logged in user
    self: UserEntity
}

export default class User extends Component<RouteComponentProps<UserProperties>, UserState> {
    private static ACCOUNT_SELF_URI: string = "/account/self";
    private static ACCOUNT_INFO_URI: string = "/account/u/";
    private static ACCOUNT_IMAGES_URI: string = "/images/";

    private nickname: string;
    private selfRequested: boolean;

    constructor(props: RouteComponentProps<UserProperties>, context: any) {
        super(props, context);
        this.nickname = this.props.match.params.nickname;
        this.selfRequested = false;
        this.state = {
            user: null,
            images: [],
            self: null
        }
    }

    componentDidMount() {
        this.refreshUser();
        this.refreshImages();
    }

    componentDidUpdate() {
        // When we change user while currently stay on the page
        // we must update its state, but do not get current user info
        // as did not changed.
        const newNickname = this.props.match.params.nickname;
        if (this.nickname != newNickname) {
            this.nickname = newNickname;
            this.refreshUser();
            this.refreshImages();
        }
    }

    render(): React.ReactNode {
        return (
            <article className="userPage article">
                <Navigation refreshFeed={this.refreshImages.bind(this)}
                            nickname={this.state.self != null ? this.state.self.nickname : ''}/>
                <Profile user={this.state.user} self={this.state.self}
                         refreshUser={this.refreshUser.bind(this)}/>
                <ImagesList images={this.state.images}/>
            </article>
        );
    }

    public refreshImages() {
        fetch(User.ACCOUNT_IMAGES_URI + this.nickname, {
            credentials: "same-origin"
        }).then(res => {
            if (res.ok) {
                res.json()
                    .then((u: UserImagesEntity[]) => {
                        this.setState({
                            images: u.length > 0 ? u[0].images : []
                        })
                    });
            }
        }).catch((e) => {
            console.log("Some error occurred: " + e);
        })
    }

    public refreshUser() {
        fetch(User.ACCOUNT_INFO_URI + this.nickname, {
            credentials: "same-origin"
        }).then(res => {
                if (res.ok) {
                    res.json()
                        .then((u: UserEntity[]) => {
                            const user = u.length > 0 ? u[0] : null;
                            if (user != null) {
                                this.setState({
                                    user: user
                                });
                                // if this user is not current user - then ask self info
                                // but do this just in case user is not current one.
                                if (!user.self && !this.selfRequested) {
                                    this.selfRequested = true;
                                    this.refreshSelf();
                                }
                            }
                        })
                }
            }
        ).catch((e) => {
            console.log("Some error occurred: " + e);
        })
    }

    public refreshSelf() {
        fetch(User.ACCOUNT_SELF_URI, {
            credentials: "same-origin"
        }).then(res => {
                if (res.ok) {
                    res.json()
                        .then((u: UserEntity) => {
                            if (u != null) {
                                this.setState({
                                    self: u
                                });
                            }
                        })
                }
            }
        ).catch((e) => {
            console.log("Some error occurred: " + e);
        })
    }
}

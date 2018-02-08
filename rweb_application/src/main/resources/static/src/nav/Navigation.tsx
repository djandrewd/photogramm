import * as React from "react";
import {Component} from "react";

import "./Navigation.css";
import Upload from "./upload/Upload";
import AppName from "./static/AppName";
import SearchPanel from "./search/SearchPanel";
import Logout from "./profile/Logout";
import Self from "./profile/Self";
import ActionHistory from "./history/ActionHistory";

interface NavigationProperties {
    refreshFeed: () => void;
    nickname: string
}

export default class Navigation extends Component<NavigationProperties, {}> {

    constructor(props: NavigationProperties) {
        super(props);
    }

    render(): React.ReactNode {
        return (
            <div className="navigation" role="navigation">
                <Upload refreshFeed={this.props.refreshFeed}/>
                <AppName/>
                <SearchPanel/>
                <Logout/>
                <Self nickname={this.props.nickname}/>
                <ActionHistory />
            </div>
        );
    }
}

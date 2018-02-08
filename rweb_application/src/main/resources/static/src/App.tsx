import * as React from "react";
import {Component} from "react";

import {Route, Switch} from 'react-router';

import Feed from "./Feed";
import Login from "./login/Login";
import User from "./user/User";
import Signup from "./login/Signup";
import ExploreTags from "./explore/ExploreTags";

export default class App extends Component<{}, {}> {

    constructor(props: {}, context: any) {
        super(props, context);
    }

    render() {
        return (
            <div>
                <Switch>
                    <Route path="/" exact
                           component={Feed}/>
                    <Route path="/signin" component={Login}/>
                    <Route path="/signup" component={Signup}/>
                    <Route path="/explore/tags/:tagName" component={ExploreTags}/>
                    <Route path="/:nickname" component={User}/>
                </Switch>
            </div>
        );
    }
};

import * as React from "react";
import {Component} from "react";

import "./Login.css";

import Credentials from "./Credentials";

export default class Login extends Component<{}, {}> {

    render(): React.ReactNode {
        return (
            <div className="login">
                <Credentials/>
            </div>)
    };

}
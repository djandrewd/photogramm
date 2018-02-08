import * as React from "react";
import {Component} from "react";

import CreateAccount from "./CreateAccount";
import * as Cookies from "js-cookie";

export default class Credentials extends Component<{}, {}> {

    private csrfToken: string;

    constructor(props: {}, context: any) {
        super(props, context);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        return (
            <form className="loginForm" action="/signin" method="post">
                <div className="container">
                    <label><b>Username</b></label>
                    <input className="loginInput" type="text" placeholder="Enter Username"
                           name="username" required/>

                    <label><b>Password</b></label>
                    <input className="loginInput" type="password" placeholder="Enter Password"
                           name="password"
                           required/>

                    <button className="loginButton" type="submit">Login</button>

                    <br/>
                    <input type="checkbox" name="remember-me"/>
                    Remember me

                    <CreateAccount/>

                    <input type="hidden" name="_csrf" value={this.csrfToken}/>
                </div>
            </form>
        );
    }
}
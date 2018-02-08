import * as React from "react";
import {Component} from "react";

import "./Logout.css";
import * as Cookies from "js-cookie";

export default class Logout extends Component<{}, {}> {

    private csrfToken: string;

    constructor(props: {}, context: any) {
        super(props, context);
        this.csrfToken = Cookies.get('XSRF-TOKEN');
    }

    render(): React.ReactNode {
        return (
            <form className="logout" method="post" action="/signout">
                <button className="btn btn-link btn-xs">
                    <span className="glyphicon glyphicon-log-out"/>
                </button>
                <input type="hidden" name="_csrf" value={this.csrfToken}/>
            </form>
        );
    }
}
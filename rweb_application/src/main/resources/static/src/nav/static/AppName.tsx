import * as React from "react";

import "./AppName.css";
import {Link} from "react-router-dom";

export default () => {
    return (
        <div className="app-name">
            <Link to="/">
                <button className="btn btn-link btn-xs">
                    <h4>Photogramm</h4>
                </button>
            </Link>
        </div>
    )
}
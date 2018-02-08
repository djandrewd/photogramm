import * as React from "react";

import "./Self.css";
import {Link} from "react-router-dom";

interface SelfProperties {
    nickname: string
}

export default (props: SelfProperties) => {
    return (
        <div className="self">
            <Link to={"/" + props.nickname}>
                <button className="btn btn-link btn-xs">
                    <span className="glyphicon glyphicon-user"/>
                </button>
            </Link>
        </div>
    );
}

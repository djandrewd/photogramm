import * as React from "react";
import {Component} from "react";

import "./UploadNavigate.css";

interface UploadNavigateProperties {
    openModal: () => void;
}

export default class UploadNavigate extends Component<UploadNavigateProperties, {}> {

    constructor(props: UploadNavigateProperties) {
        super(props);
    }

    render(): React.ReactNode {
        return (
            <div className="uploadNavigate">
                <button className="btnUpload btn bnt-link btn-sm" onClick={(e) => this.props.openModal()}>
                    <span className="glyphicon glyphicon-upload"/>
                </button>
            </div>
        );
    }
}
import * as React from "react";
import {Component} from "react";

import "./Upload.css";
import UploadNavigate from "./UploadNavigate";
import UploadDialog from "./UploadDialog";

interface UploadProperties {
    refreshFeed: () => void;
}

interface UploadState {
    isOpen: boolean;
}

export default class Upload extends Component<UploadProperties, UploadState> {

    constructor(props: UploadProperties) {
        super(props);
        this.state = {
            isOpen: false
        }
    }

    render(): React.ReactNode {
        return (
            <div className="uploadBlock">
                <UploadNavigate openModal={this.openUpload.bind(this)}/>
                <UploadDialog isOpen={this.state.isOpen} onClose={this.closeUpload.bind(this)}
                              onUpload={this.uploadFinished.bind(this)}/>
            </div>
        );
    }

    public openUpload() {
        this.setState({
            isOpen: true
        });
    }

    public closeUpload() {
        this.setState({
            isOpen: false
        });
    }

    public uploadFinished() {
        this.closeUpload();
        this.props.refreshFeed();
    }
}
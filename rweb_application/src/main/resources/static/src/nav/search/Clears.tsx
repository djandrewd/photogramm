import * as React from "react";
import {Component} from "react";

interface ClearsProperty {
    visible: boolean
    clearInput: () => void;
}

export default class Clears extends Component<ClearsProperty, {}> {
    private clearInput: () => void;

    constructor(props: ClearsProperty) {
        super(props);
        this.clearInput = props.clearInput;
    }

    render(): React.ReactNode {
        return (
            <button className="btn btn-default btn-xs clear" disabled={!this.props.visible}
                    onClick={this.clearInput.bind(this)}>
                <span className="glyphicon glyphicon-remove"/>
            </button>
        );
    }
}
import * as React from "react";
import {Component} from "react";

import "./PopupList.css";

interface PopupListProperties<T> {
    open: boolean
    elements: T[]
    converter: (e: T) => JSX.Element
    keyProvider: (e: T) => any
    close: () => void
}

export default class PopupList<T> extends Component<PopupListProperties<T>, {}> {

    private element: HTMLUListElement;
    private clickListener: (e: Event) => void;

    constructor(props: PopupListProperties<T>) {
        super(props);
        this.clickListener = (e) => {
            if (this.props.open &&
                this.element != null) {
                const clickTarget = e.srcElement;
                if (clickTarget != null) {
                    if (this.element == clickTarget) {
                        return;
                    }
                    const closest = clickTarget.closest(".popupItems");
                    if (this.element == closest) {
                        return;
                    }
                }
                this.props.close();
            }
        }
    }

    render(): React.ReactNode {
        let itemsList = null;
        if (this.props.open) {
            const elements = this.props.elements.map(e => {
                return (<li className="popupItem"
                            key={this.props.keyProvider(e)}> {this.props.converter(e)} </li>);
            });
            itemsList = (<ul ref={(e) => {
                this.element = e
            }} className="popupItems">{elements}</ul>);
        }
        return (<div className="popupList">{itemsList}</div>);
    }

    componentDidMount() {
        this.adjustListener();
    }

    componentDidUpdate() {
        this.adjustListener();
    }

    private adjustListener() {
        if (!this.props.open) {
            document.removeEventListener('click', this.clickListener);
        } else {
            document.addEventListener('click', this.clickListener);
        }
    }

}

import * as React from "react";
import {Component} from "react";
import "./ActionHistory.css";
import PopupList from "../../popup/PopupList";
import HistoryActions, {HistoryAction} from "../../model/HistoryActions";

interface HistoryState {
    popupOpen: boolean,
    historyActions: HistoryAction[]
}

export default class ActionHistory extends Component<{}, HistoryState> {
    private static FETCH_URL: string = "/social/history";

    constructor(props: {}) {
        super(props);
        this.state = {
            popupOpen: false,
            historyActions: []
        };
    }

    render(): React.ReactNode {
        return (
            <div className="userHistory">
                <button className="btn btn-link btn-xs" onClick={() => {
                    this.toggleActionHistoryPopup()
                }}>
                    <span className="glyphicon glyphicon-heart-empty"/>
                </button>

                <PopupList open={this.state.popupOpen}
                           converter={(e: HistoryAction) => ActionHistory.convertElement(e)}
                           keyProvider={(e: HistoryAction) => e.nickname + e.action + e.image.url}
                           close={this.toggleActionHistoryPopup.bind(this)}
                           elements={this.state.historyActions}/>
            </div>
        );
    }

    private static convertElement(e: HistoryAction): JSX.Element {
        const actionString = e.action == 0 ? 'liked' : 'comment';
        return (
            <div className="historyAction">
                <div className="actionDescription">
                    <b>{e.nickname}</b> {actionString} your image
                </div>
                <div className="actionImage">
                    <img className="actionImageValue" alt="Sorry..."
                         src={e.image.url}/>
                </div>
            </div>);
    }

    private fetchHistory() {
        fetch(ActionHistory.FETCH_URL, {
            credentials: "same-origin"
        }).then((r) => {
            if (r.ok) {
                r.json().then((h: HistoryActions) => {
                    this.setState({
                        popupOpen: this.state.popupOpen,
                        historyActions: h.actions
                    });
                })
            }
        }).catch((e) => {
            console.log("Error occured trying to fetch results: " + e);
        })
    }

    private toggleActionHistoryPopup() {
        if (!this.state.popupOpen) {
            this.fetchHistory();
        }
        this.setState({
            popupOpen: !this.state.popupOpen,
            historyActions: this.state.historyActions
        });
    }
}


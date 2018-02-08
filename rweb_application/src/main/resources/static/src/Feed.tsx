import * as React from "react";
import {Component} from "react";

import "./Main.css";
import FeedImages from "./images/feed/FeedImages";
import Navigation from "./nav/Navigation";
import ImageEntity from "./model/ImageEntity";
import UserWithImages from "./model/UserWithImages";

interface MainFeedState {
    nickname: string
    images: ImageEntity[];
}

export default class FeedPage extends Component<{}, MainFeedState> {
    private static FEED_URI: string = "/feed";

    constructor(props: {}) {
        super(props);
        this.state = {
            images: [],
            nickname: ''
        }
    }

    componentDidMount() {
        this.selectImages();
    }

    render() {
        return (
            <div className="Main">
                <Navigation refreshFeed={this.selectImages.bind(this)}
                            nickname={this.state.nickname}/>
                <FeedImages images={this.state.images}/>
            </div>
        );
    }

    private selectImages() {
        fetch(FeedPage.FEED_URI, {
            credentials: "same-origin"
        }).then(r => {
            if (r.ok) {
                r.json()
                    .then((response: UserWithImages[]) => {
                        this.setState({
                            images: response.length > 0 ? response[0].images : [],
                            nickname: response.length > 0 ? response[0].nickname : ''
                        });
                    })
            }
        }).catch((e) => {
            console.log("Error occurred: " + e);
            this.setState({
                images: []
            });
        });
    }
}

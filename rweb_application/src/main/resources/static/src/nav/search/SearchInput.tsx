import * as React from "react";
import {Component} from "react";
import "./SeachInput.css";
import UserEntity from "../../model/UserEntity";

interface SearchInputProperties {
    updateUsers: (searchString: string, users: UserEntity[]) => void;
    searchString: string
}

export default class SearchInput extends Component<SearchInputProperties, {}> {
    private static SEARCH_URI: string = "/search?q=";

    private textInput: HTMLInputElement;
    private updateUsers: (searchString: string, users: UserEntity[]) => void;

    constructor(props: SearchInputProperties) {
        super(props);
        this.updateUsers = props.updateUsers;
    }

    render(): React.ReactNode {
        return (
            <form className="searchForm">
                <input className="searchInput" ref={(value: HTMLInputElement) => {
                    this.textInput = value;
                }} onChange={this.findUsers.bind(this)} value={this.props.searchString}
                       placeholder="Input username" type="text"/>
            </form>
        );
    }

    private findUsers(): void {
        let searchString = this.textInput.value;
        if (searchString === '') {
            this.updateUsers(searchString, []);
            return;
        }
        fetch(SearchInput.SEARCH_URI + searchString, {
            credentials: "same-origin"
        }).then((results) => results.json())
            .then((results: UserEntity[]) => {
                this.updateUsers(searchString, results);
            })
            .catch((e) => {
                this.updateUsers(searchString, []);
                console.log("Error occured: " + e);
            });
    }
}
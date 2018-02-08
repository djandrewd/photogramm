import * as React from "react";
import {Component} from "react";

import "./Search.css";
import SearchInput from "./SearchInput";
import SearchList from "./SearchList";
import Clears from "./Clears";
import UserEntity from "../../model/UserEntity";

interface SearchUsersState {
    users: UserEntity[];
    searchString: string;
}

export default class SearchPanel extends Component<{}, SearchUsersState> {

    constructor(props: {}) {
        super(props);
        this.state = {
            users: [],
            searchString: ''
        };
    }

    render(): React.ReactNode {
        return (
            <div className="search-block dropdown">
                <SearchInput updateUsers={this.updateUsers.bind(this)}
                             searchString={this.state.searchString}/>
                <Clears clearInput={this.clearInput.bind(this)}
                        visible={this.state.searchString != ''}/>
                <SearchList users={this.state.users}/>
            </div>);
    }

    updateUsers(searchString: string, users: UserEntity[]): void {
        this.setState({
            users: users,
            searchString: searchString
        });
    }

    clearInput(): void {
        this.setState({
            users: [],
            searchString: ''
        });
    }
}
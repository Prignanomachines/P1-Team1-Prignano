import React from 'react'; 
import { useState, useEffect } from 'react'
import { user } from './User';
import '../App.css';
import { Link } from "react-router-dom";

function Nav() {

    let [userName, setUser] = useState(user.userName);

    const [, updateState] = React.useState({});
    const forceUpdate = React.useCallback(() => updateState({}), []);

    const [count, setCount] = useState(0);
    const [needUpdate, setUdp] = useState(true);

    useEffect(() => {
        //Implementing the setInterval method

        const interval = setTimeout(() => {
            if (needUpdate) {
                setUser(user.userName);
                setCount(count + 1);
                forceUpdate();
                if (userName !== "") setUdp(false);
            }
        }, 1000);

        //Clearing the interval
        return () => clearInterval(interval);
    }, [count, forceUpdate, setUdp, needUpdate, userName]);

    return (
    <div className="Nav">
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
            <div className="container-fluid">
                <Link className="navbar-brand" to="#">Navbar</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link active" aria-current="page" to="/">Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link active" aria-current="page" to="/feed">Feed</Link>
                        </li>
                        { (userName === "") ? (
                            <li className="nav-item">
                                <Link className="nav-link" to="/Login">Login</Link>
                            </li>
                            ) : (
                                    <li className="nav-item">
                                        <Link className="nav-link active" aria-current="page" to="/post">Create Post</Link>
                                    </li>
                            )
                        }
                    </ul>
                </div>
            </div>
        </nav>
        </div>
    )

}

export default Nav;

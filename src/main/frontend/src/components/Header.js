import {Link, NavLink} from "react-router-dom";
import "../css/Nav.css"

function Header() {
    return(
        <div className="nav">
            <nav>
                <NavLink className="logo" to={"/"}>C-log</NavLink>
                <span>
                    <NavLink to={"/books/bestSeller"}>베스트셀러</NavLink>
                    <NavLink to={"/books"}>도서 검색</NavLink>
                </span>
            </nav>
        </div>
    )
}

export default Header;
import "../css/Card.css"
import {NavLink} from "react-router-dom";
function BookCard(props) {
    return(
        <div className="card">
            <img src={props.book.img}/>
            <NavLink to={`/books/${props.book.isbn}`}>{props.book.name}</NavLink>
            <p>{props.book.author}</p>
            <p>{props.book.pubDate}</p>
        </div>
    )
}

export default BookCard
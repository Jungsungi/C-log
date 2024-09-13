import "../css/Card.css"
import {NavLink, useNavigate} from "react-router-dom";
function BookCard(props) {

    const navigateon = useNavigate();

    const moveBookInfo = () => {
        const isbn = props.book.isbn;
        navigateon(`/books/${props.book.isbn}`)
    }

    return(
        <div className="cardBox">
            <div className="card">
                <img src={props.book.img} onClick={moveBookInfo}/>
                <br></br>
                <NavLink className="link" to={`/books/${props.book.isbn}`}>{props.book.name}</NavLink>
                <p>{props.book.author}</p>
                <p>{props.book.pubDate}</p>
            </div>
        </div>
    )
}

export default BookCard
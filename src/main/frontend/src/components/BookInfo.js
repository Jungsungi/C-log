import {useNavigate, useParams} from "react-router-dom";
import axios, {get} from "axios";
import {useEffect, useState} from "react";
import "../css/Item.css"
import "../css/Common.css"

function BookInfo(props) {
    const {isbn} = useParams()

    useEffect(() => getBook, [])
    const [book, bookSet] = useState({})

    const navigateon = useNavigate();

    const getBook = () => {
        axios.get(`/api/item/books/${isbn}`)
            .then(res => {
                bookSet(res.data)
            }).catch(err => console.log(err))
    }

    const reviewAdd = () => {
        navigateon(`/reviews/add/${book.isbn}`)
    }

    return (
        <div className="content-body">
            <div className="item-title">
                <h1>{book.name}</h1>
            </div>
            <hr />
            <br />
            <div style={{height:"72%"}}>
                <span className="item-img" style={{float:"left", marginLeft: "50px"}}>
                    <img className="item-sub-img" src={book.img}  alt={book.name}/>
                </span>
                <span className="item-sub-span" style={{float: "right", width: "40%", marginRight: "100px"}}>
                    <div>
                        <h2>작가</h2>
                        <span>{book.author}</span>
                    </div>
                    <hr/>
                    <div>
                        <h2>출판사</h2>
                        <span>{book.publisher}</span>
                    </div>
                    <hr/>
                    <div>
                        <h2>책 소개</h2>
                        <p>{book.description}</p>
                    </div>
                    <br/><br/>
                    <hr/>
                    <div>
                        <h2>평점</h2>
                        <p>{book.grade} ({book.count} 명)</p>
                    </div>
                </span>
            </div>
            <br/>
            <div>
                <div style={{margin:"0 auto"}}>
                    <button className="btn-review-add" onClick={reviewAdd}>리뷰 등록</button>
                </div>
            </div>
        </div>
    )
}

export default BookInfo
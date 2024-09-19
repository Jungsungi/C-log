import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import "../css/ReviewAdd.css"
import "../css/Common.css"

function BookReviewAdd() {
    const {isbn} = useParams()
    const [item, setItem] = useState({})
    const [activeGrade, setActiveGrade] = useState(""); // 평점 버튼

    const gradeValue = ['1', '2', '3', '4', '5']

    useEffect((isbn) => getItem, [])

    const getItem = (itemId) => {
        axios.get(`/api/item/books/${isbn}`)
            .then(res => setItem(res.data))
            .catch(err => console.log(err))
    }

    const reviewAdd = () => {
        const reviewDemo = {...review};
        reviewDemo.itemId = item.id;
        reviewDemo.memberId = 1;

        setReview(reviewDemo);
    }

    const handleGradeClick = (grade) => {
        setActiveGrade(grade);
        setReview({ ...review, grade }); // 클릭한 등급을 리뷰 상태에 저장
    };

    const [review, setReview] = useState({
        "title" : "",
        "grade" : "",
        "content" : "",
        "memberId" : "",
        "itemId" : ""
    })

    const reviewChange = (event) => {
        setReview({...review, [event.target.name]: event.target.value})
    }


    return(
        <div className="content-body">
            <div>
                <h1 style={{textAlign : "left"}}>책 리뷰 작성</h1>
            </div>
            <div className="item-info">
                <div>
                    <input style={{width: "100%", height: "50px", fontSize: "20px"}} placeholder="제목을 입력해주세요."
                           type="text" id="title" name="title" value={review.title} onChange={reviewChange}/>
                </div>
                <div className="review-img">
                    <img src={item.img} alt={item.name}/>
                </div>
                <div className="grade-box info-box">
                    <span><h2 className="review-tag">평점</h2></span>
                    <span className="grade-btn">
                        {gradeValue.map(grade => (
                            <button
                                key={grade}
                                className={activeGrade === grade ? 'active' : ''}
                                onClick={() => handleGradeClick(grade)}
                            >
                                <label htmlFor={`grade${grade}`}>{grade}</label>
                            </button>
                        ))}
                        {gradeValue.map(grade => (
                            <input
                                key={`input${grade}`}
                                className="d-none"
                                id={`grade${grade}`}
                                type="radio"
                                name="grade"
                                value={grade}
                                checked={review.grade === grade}
                                onChange={reviewChange}
                            />
                        ))}
                    </span>
                </div>
                <div className="info-box review-name">
                    <span><h2 className="review-tag">제목</h2></span>
                    <span>
                        <h3 style={{display: "inline", marginLeft: "20px"}}>{item.name}</h3>
                    </span>
                </div>
                <div className="info-box">
                    <span><h2 className="review-tag">작가</h2></span>
                    <span>
                        <h3 style={{display: "inline", marginLeft: "20px"}}>{item.author}</h3>
                    </span>
                </div>
                <div className="info-box">
                    <span><h2 className="review-tag">출판일</h2></span>
                    <span>
                        <h3 style={{display: "inline", marginLeft: "20px"}}>{item.pubDate}</h3>
                    </span>
                </div>
                <div className="info-box">
                    <span><h2 className="review-tag">출판사</h2></span>
                    <span>
                        <h3 style={{display: "inline", marginLeft: "20px"}}>{item.publisher}</h3>
                    </span>
                </div>
            </div>
            <div className="review-info">
                <textarea name="content" value={review.content} onChange={reviewChange}
                          placeholder="리뷰 내용을 입력해 주세요."></textarea>
            </div>

            <div>
                <button onClick={reviewAdd}>리뷰 등록</button>
            </div>
        </div>
    )
}

export default BookReviewAdd
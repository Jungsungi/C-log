import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import "../css/ReviewAdd.css"

function BookReviewAdd() {
    const {isbn} = useParams()
    const [item, setItem] = useState({})
    const [activeGrade, setActiveGrade] = useState(""); // 클릭된 버튼 상태 관리

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
        <div>
            <div>
                <h1>책 리뷰 작성</h1>
            </div>
            <div>
                <div>
                    <input type="text" id="title" name="title" value={review.title} onChange={reviewChange}/>
                </div>
                <div>
                    <img src={item.img} />
                </div>
                <div className="grade-box">
                    <span>평점</span>
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
                </div>
            </div>
            <div>
                <textarea name="content" value={review.content} onChange={reviewChange}></textarea>
            </div>

            <div>
                <button onClick={reviewAdd}>리뷰 등록</button>
            </div>
        </div>
    )
}

export default BookReviewAdd
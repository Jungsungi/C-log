import logo from './logo.svg';
import './App.css';
import Header from "./components/Header";
import {BrowserRouter, Routes, Route} from "react-router-dom";
import Main from "./components/Main";
import BestSeller from "./components/BestSeller";
import Books from "./components/Books";
import BookInfo from "./components/BookInfo";
import Fotter from "./components/Fotter";
import BookReviewAdd from "./components/BookReviewAdd";

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <Header/>

            <Routes>
              <Route path="/" element={<Main />}></Route>
              <Route path="/books/bestSeller" element={<BestSeller/>}></Route>
                <Route path="/books" element={<Books/>}></Route>
                <Route path="/books/:isbn" element={<BookInfo />}></Route>
                <Route path="/reviews/add/:isbn" element={<BookReviewAdd />}></Route>
            </Routes>
      </BrowserRouter>
        <Fotter/>
    </div>
  );
}

export default App;

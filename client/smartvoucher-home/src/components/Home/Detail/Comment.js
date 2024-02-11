import React from "react";
import "../Detail/Comment.scss";
import { getAllComment, insertComment } from "../../../services/CommentService";
import { toast } from "react-toastify";
import {
  selectUserId,
  selectAvatar,
  selectUsername,
} from "../../../Redux/data/UserSlice";
import { useSelector } from "react-redux";
import Loading from "../../Util/Loading";
import moment from "moment";
import Paginate from "../../Util/Paginate";
import { selectIsAuthenticated } from "../../../Redux/data/AuthSlice";
import Account from "../../Security/Account";
import {
  getAllReply,
  insertReply,
} from "../../../services/ReplyCommentServices";

const Comment = (props) => {
  const { idWarehouse } = props;

  const [loading, setLoading] = React.useState(false);
  const [show, setShow] = React.useState(false);
  const [showInput, setShowInput] = React.useState(0);
  const [showSubInput, setShowSubInput] = React.useState(0);
  const [idCommentReply, setIdCommentReply] = React.useState(0);

  const [listComment, setListComment] = React.useState([]);
  const [listReply, setListReply] = React.useState([]);
  const [commentText, setCommentText] = React.useState("");
  const [replyText, setReplyText] = React.useState("");
  const [subReplyText, setSubReplyText] = React.useState("");
  const [replyName, setReplyName] = React.useState("");
  const [sortBy, setSortBy] = React.useState("desc");
  const [sortField, setSortField] = React.useState("id");
  const [limit, setLimit] = React.useState(6);
  const [page, setPage] = React.useState(1);
  const [totalPage, setTotalPage] = React.useState(0);
  const [totalItem, setTotalItem] = React.useState(0);

  const idUser = useSelector(selectUserId);
  const avatar = useSelector(selectAvatar);
  const userName = useSelector(selectUsername);
  const auth = useSelector(selectIsAuthenticated);

  React.useEffect(() => {
    getComment(idWarehouse, page, limit, sortBy, sortField);
    getReply();
  }, []);

  const getComment = async (idWarehouse, page, limit, sortBy, sortField) => {
    await getAllComment(idWarehouse, page, limit, sortBy, sortField)
      .then((rs) => {
        if (rs) {
          setPage(rs.page);
          setTotalItem(rs.totalItem);
          setTotalPage(rs.totalPage);
          setListComment(rs.data);
        }
      })
      .catch((err) => console.log(err));
  };

  const getReply = async () => {
    await getAllReply()
      .then((rs) => {
        if (rs) {
          setListReply(rs.data);
        }
      })
      .catch((err) => console.log(err));
  };

  const handleSendComment = async () => {
    if (!commentText) {
      return;
    }
    setLoading(true);
    const objComment = {
      idWarehouse: idWarehouse,
      idUser: idUser.id,
      commentUser: commentText,
      avatarUrl: avatar.avatar,
      userName: userName.username,
    };
    await insertComment(objComment)
      .then((rs) => {
        if (rs) {
          toast.success("Comment is successfully !");
          setLoading(false);
          setCommentText("");
          getComment(idWarehouse, page, limit, sortBy, sortField);
        }
      })
      .catch((err) => {
        console.log(err.message);
        setLoading(false);
      });
  };

  const handleInsertReply = async () => {
    if (!replyText) {
      return;
    }
    setLoading(true);
    const objReply = {
      idComment: showInput,
      idUser: idUser.id,
      replyComment: replyText,
      avatarUrl: avatar.avatar,
      userName: userName.username,
    };
    await insertReply(objReply)
      .then((rs) => {
        if (rs) {
          setLoading(false);
          setReplyText("");
          getReply();
        }
      })
      .catch((err) => {
        console.log(err.message);
        setLoading(false);
      });
  };

  const handleInsertSubReply = async () => {
    if (!subReplyText) {
      return;
    }
    setLoading(true);
    const objSubReply = {
      idComment: idCommentReply,
      idUser: idUser.id,
      replyComment: subReplyText,
      avatarUrl: avatar.avatar,
      userName: userName.username,
    };
    await insertReply(objSubReply)
      .then((rs) => {
        if (rs) {
          setLoading(false);
          setSubReplyText("");
          getReply();
        }
      })
      .catch((err) => {
        console.log(err.message);
        setLoading(false);
      });
  };

  const handlePageClick = (event) => {
    getComment(idWarehouse, +event.selected + 1, limit, sortBy, sortField);
  };

  const handleKeyPress = (event) => {
    if (event.code === "Enter") {
      if (!auth) {
        setShow(true);
        return;
      }
      handleSendComment();
      setShow(false);
    }
  };

  const handleClose = () => {
    setShow(false);
    setCommentText("");
    setReplyText("");
    setSubReplyText("");
  };

  return (
    <>
      <div className="pb-3">
        <h3>Bình luận</h3>
      </div>
      <div>
        <textarea
          placeholder="Nhập nội dung cần bình luận..."
          value={commentText}
          onChange={(event) => {
            setCommentText(event.target.value);
          }}
          onKeyDown={(event) => handleKeyPress(event)}
          className="p-3 PO"
        />
      </div>
      <div className="d-flex justify-content-end OH">
        {auth ? (
          <>
            <button
              className="btn btn-primary"
              onClick={() => handleSendComment()}
            >
              {loading && (
                <>
                  <Loading fileName={"Gửi bình luận"} />
                </>
              )}
              <i class="fa-solid fa-paper-plane"></i>
              Gửi bình luận
            </button>
          </>
        ) : (
          <>
            <button className="btn btn-primary" onClick={() => setShow(true)}>
              Gửi bình luận
            </button>
          </>
        )}
      </div>
      <div>
        {listComment &&
          listComment.map((item, key) => {
            return (
              <>
                <div key={key}>
                  <div className="d-flex">
                    <div>
                      <img
                        alt={item.username}
                        src={item.avatarUrl}
                        className="aV"
                      />
                    </div>
                    <div className="ps-3">
                      <h6 className="pm">
                        <b>{item.userName}</b>
                      </h6>
                      <p className="cL pm">
                        Bình luận vào{" "}
                        {moment(item.createdAt).format("YYYY-MM-DD HH:mm:ss")}
                      </p>
                      <p className="pm">
                        <b>{item.commentUser}</b>
                      </p>
                      {auth ? (
                        <>
                          <button
                            className="mb-3 Bi pm"
                            onClick={() => {
                              setShowInput(item.id);
                            }}
                          >
                            Trả lời
                          </button>
                        </>
                      ) : (
                        <>
                          <button
                            className="mb-3 Bi pm"
                            onClick={() => {
                              setShow(true);
                            }}
                          >
                            Trả lời
                          </button>
                        </>
                      )}
                      {showInput === item.id && (
                        <>
                          <div className="mb-3 d-flex">
                            <input
                              value={replyText}
                              className="PO"
                              onChange={(event) => {
                                setReplyText(event.target.value);
                              }}
                            />
                            <button
                              className="Bi"
                              onClick={() => handleInsertReply()}
                            >
                              <i class="fa-solid fa-paper-plane"></i>
                            </button>
                          </div>
                        </>
                      )}
                    </div>
                  </div>
                  {listReply.map((sub, key) => {
                    return (
                      item.id === sub.idComment && (
                        <>
                          <div key={key} className="ps-5 d-flex">
                            <div>
                              <img
                                alt={sub.username}
                                src={sub.avatarUrl}
                                className="Vl"
                              />
                            </div>
                            <div className="ps-3">
                              <h6 className="pm">
                                <b>{sub.userName}</b>{" "}
                                <span className="cL pm">
                                  {moment(sub.createdAt).format(
                                    "YYYY-MM-DD HH:mm:ss"
                                  )}
                                </span>
                              </h6>
                              <p className="pm">
                                <b>{sub.replyComment}</b>
                              </p>
                              {auth ? (
                                <>
                                  <button
                                    className="mb-3 Bi pm"
                                    onClick={() => {
                                      setShowSubInput(sub.id);
                                      setIdCommentReply(sub.idComment);
                                    }}
                                  >
                                    Trả lời
                                  </button>
                                </>
                              ) : (
                                <>
                                  <button
                                    className="mb-3 Bi pm"
                                    onClick={() => {
                                      setShow(true);
                                    }}
                                  >
                                    Trả lời
                                  </button>
                                </>
                              )}
                              {showSubInput === sub.id && (
                                <>
                                  <div className="mb-3 d-flex">
                                    <input
                                      value={subReplyText}
                                      className="PO"
                                      onChange={(event) => {
                                        setSubReplyText(event.target.value);
                                      }}
                                    />
                                    <button
                                      className="Bi"
                                      onClick={() => handleInsertSubReply()}
                                    >
                                      <i class="fa-solid fa-paper-plane"></i>
                                    </button>
                                  </div>
                                </>
                              )}
                            </div>
                          </div>
                        </>
                      )
                    );
                  })}
                </div>
              </>
            );
          })}
      </div>
      <div>
        <Paginate totalPages={totalPage} handlePageClick={handlePageClick} />
      </div>
      <Account show={show} handleClose={handleClose} />
    </>
  );
};

export default Comment;

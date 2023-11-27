import React, { useState } from "react";
import "./Category.scss";
import {
  addcategory,
  deletecategory,
  editcategory,
  getAllCategory,
} from "../queries/category.queries";
import { toast } from "react-toastify";
const statusHard = [
  {
    label: "Active",
    value: 1,
  },
  {
    label: "Deactive",
    value: 0,
  },
];
export const Category = () => {
  const [idDelete, setIdDelelet] = useState();
  const [list, setList] = React.useState();
  const [categoryCode, setcategoryCode] = useState("");
  const [name, setname] = useState();
  const [bannerUrl, setbannerUrl] = useState();
  const [status, setStatus] = useState();
  const [objEdit, setObjEdit] = useState();
  const [objDelete, setObjDelete] = useState();

  const handelEdit = async () => {
    if (!objEdit) {
      return alert("Please select item");
    }
    try {
      const rs = await editcategory(objEdit);
      if (rs) {
        toast.success(rs.message);
        const newspaperSpinning = [
          {
            backgroundColor: "white",
          },
          {
            backgroundColor: "rgb(221 215 209)",
          },
        ];
        const newspaperTiming = {
          duration: 500,
        };
        const ele = document.querySelector(`.wrapper .list #anim`);
        ele.animate(newspaperSpinning, newspaperTiming);
        const newlist = await getAllCategory();
        return setList(newlist.data);
      }
    } catch (error) {
      toast.error(error.message);
    }
  };
  const handelAdd = async () => {
    if (!categoryCode || !name || !status || !bannerUrl) {
      alert("Please enter enough value");
      return;
    }
    const obj = {
      categoryCode: categoryCode,
      name: name,
      status: status,
      bannerUrl: bannerUrl,
    };
    try {
      const rs = await addcategory(obj);
      if (rs?.status === "Success") {
        const newlist = await getAllCategory();
        return setList(newlist.data);
      }
      if (rs?.status === "Failed") {
        return alert(rs?.message);
      }
    } catch (error) {
      toast.error(error.message);
    }
  };
  const handelDelelet = async () => {
    if (!idDelete) {
      alert("Please enter valid ID");
      return;
    }
    try {
      const rs = await deletecategory(idDelete, objDelete);
      if (rs) {
        toast.success(rs.message);
        const newlist = await getAllCategory();
        return setList(newlist.data);
      } else {
        alert(rs?.message);
      }
    } catch (err) {
      toast.error(err.message);
    }
  };

  React.useEffect(() => {
    getAllCategory()
      .then((rs) => setList(rs.data))
      .catch((err) => toast.error(err.message));
  }, []);
  return (
    <div className="wrapper">
      <div className="list">
        <h2>Data</h2>
        <div className="item head">
          <div className="a id">ID</div>
          <div className="a">categoryCode</div>
          <div className="a">name</div>
          <div className="a">bannerUrl</div>
          <div className="a">status</div>
        </div>
        {list
          ? list?.map((item, key) => {
              return (
                <div
                  className="item"
                  key={key}
                  id={item?.id === objEdit?.id ? "anim" : ""}
                  onClick={() => {
                    setObjEdit(item);
                    setIdDelelet(item?.id);
                    setObjDelete(item);
                  }}
                >
                  <div className="a id">{item?.id}</div>
                  <div className="a">{item?.categoryCode}</div>
                  <div className="a">{item?.name}</div>
                  <div className="a">{item?.bannerUrl}</div>
                  <div
                    className={item?.status === 0 ? "a deactive" : "a active"}
                  >
                    {item?.status === 0 ? "Deactive" : "Active"}
                  </div>{" "}
                </div>
              );
            })
          : "No data"}
      </div>
      <div className="actions">
        <div className="action">
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="categoryCode"
              onChange={(e) => setcategoryCode(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="name"
              onChange={(e) => setname(e.target.value)}
            />
            <div onChange={(e) => setStatus(e.target.value)}>
              {statusHard?.map((item) => {
                return (
                  <label>
                    <input
                      checked={Number(status) === Number(item.value)}
                      type="radio"
                      value={item.value}
                    />
                    {item?.label}
                  </label>
                );
              })}
            </div>
            <input
              type="text"
              name=""
              id=""
              placeholder="bannerUrl"
              onChange={(e) => setbannerUrl(e.target.value)}
            />
          </div>
          <div className="btn" onClick={() => handelAdd()}>
            Add
          </div>
        </div>
        <div className="action">
          <h4>ID : {objEdit?.id}</h4>
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="categoryCode"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.categoryCode = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.categoryCode}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="name"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.name = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.name}
            />
            <div
              onChange={(e) => {
                let obj = { ...objEdit };
                obj.status = e.target.value;
                setObjEdit(obj);
              }}
            >
              {statusHard?.map((item) => {
                return (
                  <label>
                    <input
                      checked={Number(objEdit?.status) === Number(item.value)}
                      type="radio"
                      value={item.value}
                    />
                    {item?.label}
                  </label>
                );
              })}
            </div>
            <input
              type="text"
              name=""
              id=""
              placeholder="bannerUrl"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.bannerUrl = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.bannerUrl}
            />
          </div>
          <div className="btn" onClick={() => handelEdit()}>
            Edit
          </div>
        </div>
        <div className="action">
          <div className="form">
            <input
              type="text"
              name=""
              id=""
              placeholder="Enter valid id"
              defaultValue={idDelete}
              onChange={(e) => setIdDelelet(e.target.value)}
            />
          </div>
          <div className="btn" onClick={() => handelDelelet()}>
            Delete
          </div>
        </div>
      </div>
    </div>
  );
};

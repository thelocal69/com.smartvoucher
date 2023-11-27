import React, { useState } from "react";
import "./Discount.scss";
import {
  adddiscount,
  deletediscount,
  editdiscount,
  getAlldiscount,
} from "../queries/discount.queries";

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
export const Discount = () => {
  const [idDelete, setIdDelelet] = useState();
  const [list, setList] = React.useState();
  const [code, setCode] = useState("");
  const [name, setname] = useState();
  const [status, setStatus] = useState();
  const [objEdit, setObjEdit] = useState();
  const [objDelete, setObjDelete] = useState();

  const handelEdit = async () => {
    if (!objEdit) {
      return alert("Please select item");
    }
    const rs = await editdiscount(objEdit);
    if (rs?.status === "Success") {
      // alert(rs?.message);
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
      const newlist = await getAlldiscount();
      return setList(newlist);
    }
    if (rs?.status === "Failed") {
      alert("Edit Failed");
    }
  };
  const handelAdd = async () => {
    if (!code || !name || !status) {
      alert("Please enter enough value");
      return;
    }
    const obj = {
      code: code,
      name: name,
      status: status,
    };
    const rs = await adddiscount(obj);
    if (rs?.status === "Success") {
      const newlist = await getAlldiscount();
      return setList(newlist);
    }
    if (rs?.status === "Failed") {
      return alert(rs?.message);
    }
  };
  const handelDelelet = async () => {
    if (!idDelete) {
      alert("Please enter valid ID");
      return;
    }
    const rs = await deletediscount(idDelete, objDelete);
    if (rs?.status === "Success") {
      alert(`Delete merchant ${idDelete} successfull`);
      const newlist = await getAlldiscount();
      return setList(newlist);
    } else {
      alert(rs?.message);
    }
  };

  React.useEffect(() => {
    getAlldiscount().then((rs) => setList(rs));
    // const fn = async () => {
    //   const rs = await getAllChain();
    //   setList(rs);
    // };
    // fn();
  }, []);
  return (
    <div className="wrapper">
      <div className="list">
        <h2>Data</h2>
        <div className="item head">
          <div className="a id">ID</div>
          <div className="a">code</div>
          <div className="a">name</div>
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
                  <div className="a">{item?.code}</div>
                  <div className="a">{item?.name}</div>
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
              onChange={(e) => setCode(e.target.value)}
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
              placeholder="code"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.code = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.code}
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

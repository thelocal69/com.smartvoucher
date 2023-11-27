import React, { useState } from "react";
import "./Serial.scss";
import {
  addSerial,
  deleteSerial,
  editSerial,
  getAllSerial,
} from "../queries/serial.queries";
export const Serial = () => {
  const [idDelete, setIdDelelet] = useState();
  const [list, setList] = React.useState();
  const [batchCode, setBatchcode] = useState("");
  const [numberOfSerial, setNumberofSerial] = useState();
  const [serialCode, setSerialCode] = useState("");
  const [status, setStatus] = useState();
  const [objEdit, setObjEdit] = useState();

  const handelEdit = async () => {
    if (!objEdit) {
      return alert("Please select item");
    }
    const rs = await editSerial(objEdit);
    if (rs?.status === "OK") {
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
      const newlist = await getAllSerial();
      return setList(newlist);
    }
    if (rs?.status === "Failed") {
      alert("Edit Failed");
    }
  };
  const handelAdd = async () => {
    if (!batchCode || !numberOfSerial || !status || !serialCode) {
      alert("Please enter enough value");
      return;
    }
    const obj = {
      batchCode: batchCode,
      numberOfSerial: numberOfSerial,
      status: status,
      serialCode: serialCode,
    };
    const rs = await addSerial(obj);
    if (rs?.status === "OK") {
      const newlist = await getAllSerial();
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
    const rs = deleteSerial(idDelete);
    if (rs?.status === "OK") {
      alert(`Delete merchant ${idDelete} successfull`);
      const newlist = await getAllSerial();
      return setList(newlist);
    }
  };

  React.useEffect(() => {
    getAllSerial().then((rs) => setList(rs));
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
          <div className="a">batchCode</div>
          <div className="a">numberOfSerial</div>
          <div className="a">status</div>
          <div className="a">serialCode</div>
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
                  }}
                >
                  <div className="a id">{item?.id}</div>
                  <div className="a">{item?.batchCode}</div>
                  <div className="a">{item?.numberOfSerial}</div>
                  <div className="a">{item?.status}</div>
                  <div className="a">{item?.serialCode}</div>
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
              placeholder="batchCode"
              onChange={(e) => setBatchcode(e.target.value)}
            />
            <input
              type="number"
              name=""
              id=""
              placeholder="numberOfSerial"
              onChange={(e) => setNumberofSerial(e.target.value)}
            />
            <input
              type="number"
              name=""
              number
              id=""
              placeholder="status"
              onChange={(e) => setStatus(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="serialCode"
              onChange={(e) => setSerialCode(e.target.value)}
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
              placeholder="batchCode"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.batchCode = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.batchCode}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="numberOfSerial"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.numberOfSerial = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.numberOfSerial}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="status"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.status = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.status}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="serialCode"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.serialCode = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.serialCode}
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

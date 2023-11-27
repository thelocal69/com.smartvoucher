import React, { useState } from "react";
import "./Merchant.scss";
import {
  addMerchant,
  deleteMerchant,
  editMerchant,
  getAllMerchant,
} from "../queries/merchant.queries";
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
export const Merchant = () => {
  const [list, setList] = React.useState();
  const [merchantCode, setMerchantCode] = useState("");
  const [name, setName] = useState("");
  const [legalName, setLegalName] = useState("");
  const [logoUrl, setLogoUrl] = useState("");
  const [address, setAddress] = useState("");
  const [phone, setPhone] = useState("");
  const [email, setEmail] = useState("");
  const [desc, setDesc] = useState("");
  const [status, setStatus] = useState();
  const [objEdit, setObjEdit] = useState();
  const [objDelete, setObjDelete] = useState();

  const handelEdit = async () => {
    if (!objEdit) {
      return alert("Please select item");
    }
    editMerchant(objEdit)
      .then((rs) => {
        if (rs) {
          toast.success(rs.message);
          getAllMerchant()
            .then((rs) => setList(rs.data))
            .catch((err) => toast.error(err.message));
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };
  const handelAdd = () => {
    if (
      !merchantCode ||
      !name ||
      !legalName ||
      !logoUrl ||
      !address ||
      !phone ||
      !email ||
      !desc ||
      !status
    ) {
      alert("Please enter enough value");
      return;
    }
    const obj = {
      merchantCode: merchantCode,
      name: name,
      legalName: legalName,
      logoUrl: logoUrl,
      address: address,
      phone: phone,
      email: email,
      description: desc,
      status: status,
    };
    addMerchant(obj)
      .then((rs) => {
        if (rs) {
          toast.success(rs.message);
          getAllMerchant()
            .then((rs) => setList(rs.data))
            .catch((err) => toast.error(err.message));
        }
      })
      .catch((err) => {
        toast.error(err.message);
      });
  };
  const handelDelelet = () => {
    if (!objDelete) {
      alert("Please enter valid ID");
      return;
    }
    deleteMerchant(objDelete)
      .then((rs) => {
        if (rs) {
          toast.success(rs.message);
          getAllMerchant()
            .then((rs) => setList(rs.data))
            .catch((err) => toast.error(err.message));
        }
      })
      .catch((err) => toast.error(err.message));
  };

  React.useEffect(() => {
    getAllMerchant()
      .then((rs) => setList(rs.data))
      .catch((err) => toast.error(err.message));
  }, []);
  return (
    <div className="wrapper">
      <div className="list">
        <h2>Data</h2>
        <div className="item head">
          <div className="a id">ID</div>
          <div className="a">merchantCode</div>
          <div className="a">name</div>
          <div className="a">status</div>
          <div className="a">legalName</div>
          <div className="a">logoUrl</div>
          <div className="a">address</div>
          <div className="a">phone</div>
          <div className="a">email</div>
          <div className="a">description</div>
        </div>
        {list
          ? list?.map((item, key) => {
              return (
                <div
                  className="item"
                  key={key}
                  onClick={() => {
                    setObjEdit(item);
                    setObjDelete(item);
                  }}
                >
                  <div className="a id">{item?.id}</div>
                  <div className="a">{item?.merchantCode}</div>
                  <div
                    className={item?.status === 0 ? "a deactive" : "a active"}
                  >
                    {item?.status === 0 ? "Deactive" : "Active"}
                  </div>
                  <div className="a">{item?.name}</div>
                  <div className="a">{item?.legalName}</div>
                  <div className="a">{item?.logoUrl}</div>
                  <div className="a">{item?.address}</div>
                  <div className="a">{item?.phone}</div>
                  <div className="a">{item?.email}</div>
                  <div className="a">{item?.description}</div>
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
              placeholder="merchantCode"
              onChange={(e) => setMerchantCode(e.target.value)}
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
              placeholder="name"
              onChange={(e) => setName(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="legalName"
              onChange={(e) => setLegalName(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="logoURl"
              onChange={(e) => setLogoUrl(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="address"
              onChange={(e) => setAddress(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="phone"
              onChange={(e) => setPhone(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="email"
              onChange={(e) => setEmail(e.target.value)}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="description"
              onChange={(e) => setDesc(e.target.value)}
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
              placeholder="merchantCode"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.merchantCode = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.merchantCode}
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
              placeholder="name"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.name = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.name}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="legalName"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.legalName = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.legalName}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="logoURl"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.logoUrl = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.logoUrl}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="address"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.address = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.address}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="phone"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.phone = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.phone}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="email"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.email = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.email}
            />
            <input
              type="text"
              name=""
              id=""
              placeholder="description"
              onChange={(e) => {
                let ob = { ...objEdit };
                ob.description = e.target.value;
                setObjEdit(ob);
              }}
              defaultValue={objEdit?.description}
            />
          </div>
          <div className="btn" onClick={() => handelEdit()}>
            Edit
          </div>
        </div>
        <div className="action">
          <div className="form">
            <div>{objDelete?.id}</div>
          </div>
          <div className="btn" onClick={() => handelDelelet()}>
            Delete
          </div>
        </div>
      </div>
    </div>
  );
};

import os
from doctest import debug
from flask import Flask, render_template, request, session, redirect, flash, send_file
from flask.sessions import SecureCookieSession
from werkzeug.utils import secure_filename
from DBConnection import Db
from datetime import datetime

app = Flask(__name__, template_folder='template', static_url_path='/static/')

app.secret_key = "asdff"

@app.route('/')
def ind():
    return render_template("HomePage.html")
@app.route('/HomePage')
def HomePage():
    return render_template("HomePage.html")
@app.route('/Login')
def login():
    return render_template("login.html")
@app.route('/Adminhome')
def Adminhome():
    return render_template("AdminHome.html")

@app.route('/SearchFine')
def SearchFine():
    return render_template("Search.html")

@app.route('/LogOut')
def logOut():
    return render_template("LogOut.html")

@app.route('/login1', methods=['post'])
def login1():
    name = request.form['un']
    password = request.form['pass']
    session['lid'] = name
    qry = "select * from login where admin_id='" + name + "' and password='" + password + "'"
    ob = Db()
    print(qry)
    res = ob.selectOne(qry)
    if res is not None:
        return Adminhome()

    return "<script>alert('invalid');window.location='/';</script>"
#-------------------------------------admin---------------------------------------#
@app.route('/viewDistrict')
def viewDistrict():
    ob = Db()
    data = None
    res = ob.select("select * from district")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('viewDistrict.html', data=res)
@app.route('/LinkaddTollPlace')
def LinkaddTollPlace():
    id = request.args.get('id')
    session['did']=id
    return render_template("AddTollPlace.html")
@app.route('/AddTollPlace',methods=['post'])
def AddTollPlace():
    did=session['did']
    name = request.form['TxtName']
    latitude = request.form['TxtLatitude']
    longitude = request.form['TxtLongitude']
    ob = Db()
    q = "insert into toll_place values(null,'" + did + "','" + latitude + "','" + longitude + "','" + name + "')"
    ob.insert(q)
    return "<script>alert('Inserted succesfully');window.location='/Adminhome';</script>"
@app.route('/ViewTollPlace')
def ViewTollPlace():
    id = request.args.get('id')
    ob = Db()
    data = None
    res = ob.select("select * from toll_place where iddistrict='"+id+"'")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewTollPlace.html', data=res)
@app.route('/SearchVehicle',methods=['post'])
def SearchVehicle():
    vNumber = request.form['TxtVehicle']
    ob = Db()
    data = None
    res = ob.select("SELECT f.fine_type,f.amount,f.speed_limit,uf.current_speed,uf.fine_date FROM `aghosh_toll_application`.`user_fine` as uf join fine as f on uf.idfine_type=f.idfine where uf.vehicle_no='"+vNumber+"' ORDER BY uf.fine_date desc")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewResult.html', data=res)

@app.route('/Location')
def ViewLocation():
    lat = request.args.get('lat')
    lon = request.args.get('lon')
    return render_template("ViewLocation.html", lon=lon, lat=lat)
@app.route('/deletetollplace')
def deletetollplace():
    id = request.args.get('id')
    ob = Db()
    q = "delete from toll_place where idtoll_place='"+id+"'"
    ob.delete(q)
    return "<script>alert('deleted succesfully');window.location='/Adminhome';</script>"
@app.route('/ViewVehicle')
def ViewVehicle():
    id = request.args.get('id')
    session['tollid']=id
    ob = Db()
    data = None
    res = ob.select("select * from vehicle")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewVehicle.html', data=res)
@app.route('/LinkAddRate')
def LinkAddRate():
    id = request.args.get('id')
    session['vid']=id
    return render_template("AddRate.html")
@app.route('/AddTollAmount',methods=['post'])
def AddTollAmount():
    tollid=session['tollid']
    vid=session['vid']
    amount= request.form['TxtAmount']
    direction = request.form['TxtDirection']
    ob = Db()
    q = "insert into toll_place_amount values(null,'" + tollid + "','" + vid + "','" + amount + "','"+direction+"')"
    ob.insert(q)
    return "<script>alert('Inserted succesfully');window.location='/Adminhome';</script>"
@app.route('/ViewTollAmount')
def ViewTollAmount():
    id = request.args.get('id')
    ob = Db()
    data = None
    res = ob.select("select ta.*,v.* from toll_place_amount as ta join vehicle as v on ta.idvehicle=v.idvehicle where ta.idtoll_place='"+id+"'")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewTollAmount.html', data=res)
@app.route('/LinkEditRate')
def LinkEditRate():
    id = request.args.get('id')
    ob = Db()
    data = None
    res = ob.selectOne("select * from toll_place_amount where idtoll_place_amount='" + id + "'")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('EditAmount.html', data=res)
@app.route('/EditAmount',methods=['post'])
def EditAmount():
    id = request.form['id']
    amount = request.form['TxtAmount']
    direction = request.form['TxtDirection']
    ob = Db()
    q = "update toll_place_amount set amount='" + amount + "',journey_ticket='" + direction + "' where idtoll_place_amount='"+id+"'"
    ob.update(q)
    return "<script>alert('Updated succesfully');window.location='/Adminhome';</script>"
@app.route('/DeleteRate')
def DeleteRate():
    id = request.args.get('id')
    ob = Db()
    q = "delete from toll_place_amount where idtoll_place_amount='" + id + "'"
    ob.delete(q)
    return "<script>alert('Deleted succesfully');window.location='/Adminhome';</script>"
@app.route('/ViewFeedback')
def ViewFeedback():
    ob = Db()
    data = None
    res = ob.select("select * from feedback")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewFeedback.html', data=res)
@app.route('/LinkReply')
def LinkReply():
    id = request.args.get('id')
    session['fid']=id
    return render_template("Reply.html")
@app.route('/FeedbackReply',methods=['post'])
def FeedbackReply():
    id=session['fid']
    reply = request.form['TxtReply']
    ob = Db()
    q = "update feedback set reply='" + reply + "'where idfeedback='" + id + "'"
    ob.update(q)
    return "<script>alert('Updated succesfully');window.location='/Adminhome';</script>"
@app.route('/LinkAddFine')
def LinkAddFine():
    return render_template("AddFine.html")
@app.route('/AddFineType',methods=['post'])
def AddFineType():
    finetype = request.form['TxtFine']
    amount = request.form['TxtAmount']
    latitude = request.form['TxtLatitude']
    longitude = request.form['TxtLongitude']
    limit = request.form['TxtLimit']
    ob = Db()
    q = "insert into fine values(null,'" + finetype + "','" + amount + "','"+latitude+"','"+longitude+"','"+limit+"')"
    ob.insert(q)
    return "<script>alert('Inserted succesfully');window.location='/Adminhome';</script>"
@app.route('/ViewFine')
def ViewFine():
    ob = Db()
    data = None
    res = ob.select("select * from fine")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('ViewFine.html', data=res)
@app.route('/LinkEditFine')
def LinkEditFine():
    id = request.args.get('id')
    ob = Db()
    data = None
    res = ob.selectOne("select * from fine where idfine='"+id+"'")
    if res:
        data = res
    if data == None:
        return "<script>alert('No Data Found');window.location='/Adminhome';</script>"
    return render_template('EditFine.html', data=res)
@app.route('/EditFine',methods=['post'])
def EditFine():
    id = request.form['id']
    finetype = request.form['TxtFine']
    amount = request.form['TxtAmount']
    latitude = request.form['TxtLatitude']
    longitude = request.form['TxtLongitude']
    limit = request.form['TxtLimit']
    ob = Db()
    q = "update fine set fine_type='"+finetype+"',amount='"+amount+"',latitude='"+latitude+"',longitude='"+longitude+"',speed_limit='"+limit+"' where idfine='"+id+"'"
    ob.update(q)
    return "<script>alert('Updated succesfully');window.location='/Adminhome';</script>"
@app.route('/DeleteFine')
def DeleteFine():
    id = request.args.get('id')
    ob = Db()
    q = "delete from fine where idfine='" + id + "'"
    ob.delete(q)
    return "<script>alert('Deleted succesfully');window.location='/Adminhome';</script>"

if __name__ == '__main__':
    app.run(debug=True)
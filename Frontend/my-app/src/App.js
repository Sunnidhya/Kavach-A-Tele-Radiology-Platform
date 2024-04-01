import './App.css';
import AdminLogin from './Components/Admin/AdminLogin';
import DoctorLanding from './Components/Doctor/DoctorLanding/DoctorLanding';
import DoctorLogin from './Components/Doctor/Login/DoctorLogin';
import FirstPage from './Components/FirstPage';
import LabLogin from './Components/Lab/Login/LabLogin';
import PatientLogin from './Components/Patient/Login/PatientLogin';
import PatientLanding from './Components/Patient/PatientLanding/PatientLanding';
import RegPatient from './Components/Patient/Registration/RegPatient';
import RadioLogin from './Components/Radiologist/Login/RadioLogin';
import { Routes, Route } from 'react-router-dom';
import LabLanding from './Components/Lab/Landing/LabLanding';
import RadioLanding from './Components/Radiologist/Landing/RadiologistLanding';
import RadiologistDetails from './Components/Radiologist/Details/RadiologistDetails';
import PatientProfile from './Components/Patient/PatientProfile/PatientProfile';
import RadiologistProfile from './Components/Radiologist/Profile/RadiologistProfile';
import LabProfile from './Components/Lab/LabProfile/LabProfile';
import DoctorProfile from './Components/Doctor/DoctorProfile/DoctorProfile';
import DoctorDetails from './Components/Doctor/Details/DoctorDetails';
import PatDetails from './Components/Patient/Details/PatientDetails';
import AdminHomePage from './Components/Admin/AdminHomePage/AdminHomePage';
import AdminLanding from './Components/Admin/AdminLanding/AdminLanding';
import PrivateRoutes from './Auth/PrivateRoutes';



function App() {
  return (
    <Routes>
    <Route path="/" element={<FirstPage/>}/>
    <Route path="/admin" element={<AdminLogin/>}/>
    <Route path="/doctor" element={<DoctorLogin/>}/>
    <Route path="/radiologist" element={<RadioLogin/>}/>
    <Route path="/lab" element={<LabLogin/>}/>
    <Route path="/patient" element={<PatientLogin/>}/>
    <Route path="/patient/register" element={<RegPatient/>}/>
    <Route path="/admin/landing" element={<AdminLanding/>}/>
    <Route element={<PrivateRoutes/>}>
         <Route path="/doctor/landing" element={<DoctorLanding/>}/>
         <Route path="/patient/landing" element={<PatientLanding/>}/>
         <Route path="/lab/landing" element={<LabLanding/>}/>
         <Route path="/radiologist/landing" element={<RadioLanding/>}/>
         <Route path="/radiologist/profile" element={<RadiologistProfile/>}/>
         <Route path="/patient/profile" element={<PatientProfile/>}/>
         <Route path="/lab/profile" element={<LabProfile/>}/>
         <Route path="/doctor/profile" element={<DoctorProfile/>}/>
         <Route path="/radiologist/details" element={<RadiologistDetails/>}/>
         <Route path="/doctor/details" element={<DoctorDetails/>}/>
         <Route path="/patient/details" element={<PatDetails/>}/>
         <Route path="/patient/register" element={<RegPatient/>}/>
    </Route>
    <Route path="/admin/homepage" element={<AdminHomePage/>}/>
  </Routes>
  );
}

export default App;

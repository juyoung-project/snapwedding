"use client"

export default function Login() {

    const handleNaverLogin = () => {
        window.location.href = "http://localhost:8090/oauth2/authorization/naver";
    }

    const handleSignup = () => {
        window.location.href = "http://localhost:8090/oauth2/authorization/naver";
    }

    return (
        <div className="flex flex-col items-center justify-center h-screen">
            <h1 className="text-2xl font-bold"></h1>
            <div className="flex flex-col items-center justify-center gap-2 mt-10">
                <input type="text" placeholder="이메일" className="border-2 border-gray-300 rounded-md p-2" />
                <input type="password" placeholder="비밀번호" className="border-2 border-gray-300 rounded-md p-2" />
                <button className="bg-violet-500 text-white p-2 rounded-md w-full">로그인</button>
                <button className="bg-violet-500 text-white p-2 rounded-md w-full" onClick={() => {
                    handleSignup()
                }}>
                    회원가입
                </button>
            </div>

            <div className="flex flex-row items-center justify-center gap-2 mt-10">
                <button className="bg-green-500 text-white p-2 rounded-md w-full" onClick={() => {
                    handleNaverLogin()
                }}>
                    네이버로그인
                </button>
                <button className="bg-yellow-500 text-white p-2 rounded-md w-full" onClick={() => {
                    handleNaverLogin()
                }}>
                    카카오로그인
                </button>
            </div>
        </div>
    );
}
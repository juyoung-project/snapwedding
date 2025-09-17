export const Header = () => {
    return (
        <div
            data-testid="header"
            className="flex items-center justify-between border-b border-b-gray-300 bg-white py-[9px] pr-4 pl-6"
        >
            <div data-testid="header-left" className="flex items-center gap-2"></div>
            <div data-testid="header-right" className="flex">
                <div data-testid="buttons" className="flex gap-3">
                    <div className="p-2">
                        11
                    </div>
                    <div className="p-2">
                        22
                    </div>
                </div>
                <div data-testid="user" className="flex items-center px-2 py-[5px] text-[14px]">
                    <span className="font-semibold">xxx 님 </span>환영합니다
                </div>
            </div>
        </div>
    );
};

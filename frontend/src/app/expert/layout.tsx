import { Header } from '@/components/header';
import { ExpertSidebar } from '@/components/expert-sidebar';
import { SidebarProvider } from '@/components/ui/sidebar';

export default function ExpertLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="h-full">
      <div className="flex flex-col h-full">
        <Header />
        <div className="flex flex-1">
          <div className="relative h-full w-fit flex-shrink-0 gap-1 bg-white px-3 py-[18px]">
            <SidebarProvider>
              <ExpertSidebar />
            </SidebarProvider>
          </div>
          {children}
        </div>
      </div>
    </div>
  );
}

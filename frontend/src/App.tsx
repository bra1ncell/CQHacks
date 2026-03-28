import { useState } from "react";
import EventsView from "./EventsView.tsx";
import RaceLiveView from "./RaceLiveView.tsx";
import StartScreen, { type StartDestination } from "./StartScreen.tsx";

type Screen = "menu" | StartDestination;

export default function App() {
  const [screen, setScreen] = useState<Screen>("menu");

  if (screen === "menu") {
    return (
      <StartScreen
        onNavigate={(dest) => {
          setScreen(dest);
        }}
      />
    );
  }

  if (screen === "events") {
    return <EventsView onBack={() => setScreen("menu")} />;
  }

  return <RaceLiveView onBack={() => setScreen("menu")} />;
}

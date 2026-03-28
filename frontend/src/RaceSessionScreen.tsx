import { useCallback, useState } from "react";
import { raceForRound } from "./racesData.ts";
import "./RaceSessionScreen.css";

type Props = {
  round: number;
  onBack: () => void;
  onOpenRaceLive: () => void;
};

type ModalTab = "code" | "output";

async function fetchText(url: string): Promise<string> {
  const res = await fetch(url);
  if (!res.ok) {
    const t = await res.text();
    throw new Error(t || res.statusText);
  }
  return res.text();
}

export default function RaceSessionScreen({ round, onBack, onOpenRaceLive }: Props) {
  const race = raceForRound(round);
  const [modalOpen, setModalOpen] = useState(false);
  const [tab, setTab] = useState<ModalTab>("code");
  const [codeText, setCodeText] = useState<string | null>(null);
  const [outputText, setOutputText] = useState<string | null>(null);
  const [loadError, setLoadError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const openModal = useCallback(() => {
    setModalOpen(true);
    setLoadError(null);
    setCodeText(null);
    setOutputText(null);
    setTab("code");
    setLoading(true);
    Promise.all([
      fetchText("/api/demo/main-source"),
      fetchText(`/api/demo/main-output?round=${encodeURIComponent(String(round))}`),
    ])
      .then(([code, out]) => {
        setCodeText(code);
        setOutputText(out);
      })
      .catch((e: unknown) => {
        setLoadError(e instanceof Error ? e.message : "Request failed");
      })
      .finally(() => setLoading(false));
  }, [round]);

  const closeModal = useCallback(() => {
    setModalOpen(false);
  }, []);

  return (
    <div className="race-session">
      <button type="button" className="race-session__back" onClick={onBack}>
        ← Back to Race Selector
      </button>

      <main className="race-session__main">
        <h1 className="race-session__title">{race?.name ?? "Grand Prix"}</h1>
        <p className="race-session__loc">{race?.location ?? ""}</p>
        <p className="race-session__placeholder">
          Barebones session view — hook up timing, standings, or stream here.
        </p>
        <div className="race-session__actions">
          <button type="button" className="race-session__cta race-session__cta--secondary" onClick={openModal}>
            View team Main.java
          </button>
          <button type="button" className="race-session__cta" onClick={onOpenRaceLive}>
            Open Race Live (data view)
          </button>
        </div>
      </main>

      {modalOpen && (
        <div className="race-session-modal" role="dialog" aria-modal="true" aria-label="Backend Main.java demo">
          <div className="race-session-modal__backdrop" onClick={closeModal} aria-hidden />
          <div className="race-session-modal__panel">
            <header className="race-session-modal__head">
              <h2 className="race-session-modal__title">Backend team Main → Spring Boot</h2>
              <button type="button" className="race-session-modal__close" onClick={closeModal} aria-label="Close">
                ×
              </button>
            </header>
            <div className="race-session-modal__tabs" role="tablist">
              <button
                type="button"
                role="tab"
                aria-selected={tab === "code"}
                className={tab === "code" ? "race-session-modal__tab race-session-modal__tab--on" : "race-session-modal__tab"}
                onClick={() => setTab("code")}
              >
                Source code
              </button>
              <button
                type="button"
                role="tab"
                aria-selected={tab === "output"}
                className={tab === "output" ? "race-session-modal__tab race-session-modal__tab--on" : "race-session-modal__tab"}
                onClick={() => setTab("output")}
              >
                Console-style output
              </button>
            </div>
            <div className="race-session-modal__body">
              {loading && <p className="race-session-modal__status">Loading…</p>}
              {loadError && <p className="race-session-modal__error">{loadError}</p>}
              {!loading && !loadError && tab === "code" && (
                <pre className="race-session-modal__pre">{codeText ?? ""}</pre>
              )}
              {!loading && !loadError && tab === "output" && (
                <pre className="race-session-modal__pre">{outputText ?? ""}</pre>
              )}
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

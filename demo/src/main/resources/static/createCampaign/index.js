async function createCampaign() {
  const file = document.getElementById("campaign-file").files[0];
  const date = document.getElementById("campaign-date").value;
  const errorEl = document.getElementById("campaign-error");
  const responseEl = document.getElementById("campaign-response");

  errorEl.textContent = "";
  responseEl.textContent = "";

  if (!file || !date) {
    errorEl.textContent = "⚠️ Please select a file and date.";
    return;
  }

  const formData = new FormData();
  formData.append("file", file);
  formData.append("dateTime", date);

  try {
    const response = await fetch("http://localhost:8080/campaign/create", {
      method: "POST",
      body: formData
    });

    if (response.ok) {
      const text = await response.text();
      responseEl.textContent = `✅ ${text}`;

      // Optionally, redirect to logs page after a delay
      setTimeout(() => {
        window.location.href = "../FileLog/FileLog.html";
      }, 1500);
    } else {
      errorEl.textContent = "❌ Failed to create campaign.";
    }
  } catch (err) {
    console.error("Error:", err);
    errorEl.textContent = "❌ Something went wrong while connecting to backend.";
  }
}

using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.SSLCertificateChecker
{
    /// <summary>
    /// Query options for the SSL Certificate Checker API
    /// </summary>
    public class SSLCertificateCheckerQueryOptions
    {
        /// <summary>
        /// The domain of the website to check the SSL certificate of
        /// Example: ebay.com
        /// </summary>
        [JsonProperty("domain")]
        public string Domain { get; set; }
    }
}

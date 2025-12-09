declare module '@apiverve/sslchecker' {
  export interface sslcheckerOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface sslcheckerResponse {
    status: string;
    error: string | null;
    data: SSLCertificateCheckerData;
    code?: number;
  }


  interface SSLCertificateCheckerData {
      subject:        Issuer;
      issuer:         Issuer;
      subjectaltname: string;
      infoAccess:     { [key: string]: string[] };
      ca:             boolean;
      bits:           number;
      validFrom:      string;
      validTo:        string;
      serialNumber:   string;
      domain:         string;
  }
  
  interface Issuer {
      c:  string;
      st: string;
      l?: string;
      o:  string;
      cn: string;
  }

  export default class sslcheckerWrapper {
    constructor(options: sslcheckerOptions);

    execute(callback: (error: any, data: sslcheckerResponse | null) => void): Promise<sslcheckerResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: sslcheckerResponse | null) => void): Promise<sslcheckerResponse>;
    execute(query?: Record<string, any>): Promise<sslcheckerResponse>;
  }
}
